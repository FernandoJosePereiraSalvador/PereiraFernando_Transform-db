/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Controller;

import Clases.Astronauta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Transform class represents a data transformation process from a
 * relational database to an ObjectDB database. It extracts specified tables
 * from the relational database, transforms the data, and persists it into an
 * ObjectDB database. The transformation is executed using the provided database
 * name, list of selected tables, and the path to the ObjectDB database file.
 *
 * @author Fernando
 */
public class Transform {

    private String databaseName;
    private List<String> selectedTables;

    /**
     * Constructs a Transform object with the specified database name and
     * selected tables.
     *
     * @param databaseName The name of the relational database.
     * @param selectedTables The list of tables selected for transformation.
     */
    public Transform(String databaseName, List<String> selectedTables) {
        this.databaseName = databaseName;
        this.selectedTables = selectedTables;
    }

    /**
     * Executes the data transformation process by extracting data from the
     * selected tables in the relational database and persisting it into an
     * ObjectDB database.
     *
     * @param objectDBPath The path to the ObjectDB database file.
     */
    public void executeTransformation(String objectDBPath) {
        try (Connection connection = ConnectionDB.getConnection()) {
            // Extract data from specified tables
            List<Object> extractedData = new ArrayList<>();
            for (String tableName : this.selectedTables) {
                Class<?> entityClass = getEntityClassForTableName(tableName);
                List<?> tableData = extract_data(connection, tableName, entityClass);
                extractedData.addAll(tableData);
            }

            // Persist data in ObjectDB
            persist(extractedData, objectDBPath);

            System.out.println("Conversión exitosa.");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Extracts data from a specified table in the relational database and maps
     * it to entity objects.
     *
     * @param connection The connection to the relational database.
     * @param tableName The name of the table to extract data from.
     * @param entityClass The class representing the entity corresponding to the
     * table.
     * @param <T> The type of the entity class.
     * @return A list of entity objects representing the extracted data.
     * @throws SQLException If an SQL exception occurs during data extraction.
     */
    public static <T> List<T> extract_data(Connection connection, String tableName, Class<T> entityClass) throws SQLException {
        List<T> entities = new ArrayList<>();

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {
            while (resultSet.next()) {
                try {
                    // Create a new instance of the entity class
                    Constructor<T> constructor = entityClass.getDeclaredConstructor();
                    T entity = constructor.newInstance();

                    // Get the fields of the entity class
                    Field[] fields = entityClass.getDeclaredFields();

                    // Iterate over the fields and map them to columns in the result set
                    for (Field field : fields) {
                        field.setAccessible(true);

                        // Determine the column name based on the presence of the @Column annotation
                        String columnName = field.isAnnotationPresent(Column.class)
                                ? field.getAnnotation(Column.class).name()
                                : field.getName();

                        if (!field.getType().isPrimitive() && !field.getType().equals(String.class) && !field.getType().equals(Date.class) && !field.getType().equals(BigDecimal.class)) {
                            // Handle nested objects
                            Class<?> nestedType = field.getType();
                            Object nestedEntity = extractNestedEntity(connection, resultSet.getObject(columnName), nestedType);
                            field.set(entity, nestedEntity);
                        } else {
                            // Set the field value with the corresponding column value in the database
                            field.set(entity, resultSet.getObject(columnName));
                        }
                    }

                    entities.add(entity);

                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    System.out.println(e);
                }
            }
        }
        return entities;
    }

    /**
     * Extracts a nested entity by performing a recursive database query based
     * on the given column value and entity class.
     *
     * @param connection The connection to the relational database.
     * @param columnValue The value of the column used for the database query.
     * @param entityClass The class representing the nested entity.
     * @param <T> The type of the nested entity class.
     * @return The populated nested entity object.
     * @throws SQLException If an SQL exception occurs during the database
     * query.
     */
    private static <T> T extractNestedEntity(Connection connection, Object columnValue, Class<T> entityClass) throws SQLException {
        Constructor<T> constructor;
        try {
            constructor = entityClass.getDeclaredConstructor();
            T nestedEntity = constructor.newInstance();

            if (columnValue != null) {
                Statement statement2 = connection.createStatement();

                Field[] fields = entityClass.getDeclaredFields();
                Field idField = null;

                // Find the field with the annotation @Id
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Id.class)) {
                        idField = field;
                        break;
                    }
                }

                // Check if the primary key field was found
                if (idField == null) {
                    throw new RuntimeException("Campo de clave primaria no encontrado en la entidad: " + entityClass.getName());
                }

                String columnName = idField.isAnnotationPresent(Column.class)
                        ? idField.getAnnotation(Column.class).name()
                        : idField.getName();

                String result2 = "SELECT * FROM " + entityClass.getSimpleName() + " WHERE " + columnName + " = " + columnValue;
                ResultSet resultSet2 = statement2.executeQuery(result2);

                if (resultSet2.next()) {
                    for (Field field : fields) {
                        field.setAccessible(true);

                        // Ignore fields that are collections
                        if (Collection.class.isAssignableFrom(field.getType())) {
                            continue;
                        }

                        String fieldColumnName = field.isAnnotationPresent(Column.class)
                                ? field.getAnnotation(Column.class).name()
                                : field.getName();

                        if (!field.getType().isPrimitive() && !field.getType().equals(String.class) && !field.getType().equals(Date.class) && !field.getType().equals(BigDecimal.class)) {
                            // Recursively handle nested objects
                            Class<?> nestedType = field.getType();
                            Object nestedFieldValue = extractNestedEntity(connection, resultSet2.getObject(fieldColumnName), nestedType);
                            field.set(nestedEntity, nestedFieldValue);
                        } else {
                            // Set field with column value in database
                            field.set(nestedEntity, resultSet2.getObject(fieldColumnName));
                        }
                    }
                }
            }

            return nestedEntity;

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Persists (saves) a list of entities into an ObjectDB database at the
     * specified path.
     *
     * @param entities The list of entities to be persisted.
     * @param objectDBPath The path to the ObjectDB database file.
     * @param <T> The type of the entities.
     */
    public static <T> void persist(List<T> entities, String objectDBPath) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:" + objectDBPath);
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        for (T entity : entities) {
            persistEntity(em, entity);
        }

        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    /**
     * Persists a single entity, handling nested objects recursively.
     *
     * @param em The EntityManager for ObjectDB.
     * @param entity The entity to be persisted.
     * @param <T> The type of the entity.
     */
    private static <T> void persistEntity(EntityManager em, T entity) {
        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            // Check if field is an object
            if (!field.getType().isPrimitive() && !field.getType().equals(String.class)
                    && !field.getType().equals(Date.class) && !field.getType().equals(BigDecimal.class)) {
                try {
                    // Get nested object
                    Object nestedObject = field.get(entity);

                    // Check if it is not null and is not a non-entity collection
                    if (nestedObject != null && !(nestedObject instanceof Set)) {
                        // Persist nested object recursively
                        persistEntity(em, nestedObject);
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("Error: " + e);
                }
            }
        }

        if (!(entity instanceof Set)) {
            em.persist(entity);
        }
    }

    /**
     * Maps the name of a table to the corresponding entity class.
     *
     * @param tableName The name of the table.
     * @return The Class object representing the entity.
     * @throws IllegalArgumentException If the table name is unknown or not
     * mapped.
     */
    private static Class<?> getEntityClassForTableName(String tableName) {
        switch (tableName.toLowerCase()) {
            case "astronauta" -> {
                return Astronauta.class;
            }
            default -> throw new IllegalArgumentException("Tabla desconocida: " + tableName);
        }
    }
}
