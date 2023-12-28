/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Fernando
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing database connections and metadata.
 *
 * @author Fernando
 */
public class ConnectionDB {

    // JDBC URL template for MySQL
    private static String jdbc_url = "jdbc:mysql://localhost:3308/%s?useSSL=false";
    private static String username = "root";
    private static String password = "1234";
    private static Connection connection;
    private static String databaseName = "NASA_DB";

    /**
     * Private constructor to prevent instantiation
     */
    private ConnectionDB() {
    }

    /**
     * Retrieves or establishes a connection to the database.
     *
     * @return The database connection.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String jdbcUrl = String.format(jdbc_url, databaseName);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        }
        return connection;
    }

    /**
     * Sets the current database name.
     *
     * @param databaseName The name of the database.
     */
    public static void setDatabaseName(String databaseName) {
        ConnectionDB.databaseName = databaseName;
    }

    /**
     * Sets the current jdbc_url.
     *
     * @param jdbcUrlTemplate The jdbcUrlTemplate
     */
    public static void setJdbc_url(String jdbcUrlTemplate) {
        ConnectionDB.jdbc_url = jdbcUrlTemplate;
    }

    /**
     * Sets the current username.
     *
     * @param username
     */
    public static void setUsername(String username) {
        ConnectionDB.username = username;
    }

    /**
     * Sets the current password
     *
     * @param password The password
     */
    public static void setPassword(String password) {
        ConnectionDB.password = password;
    }

    /**
     * Retrieves a list of available database names.
     *
     * @return A list of database names.
     * @throws SQLException If a database access error occurs.
     */
    public static List<String> getDatabaseNames() throws SQLException {
        List<String> databaseNames = new ArrayList<>();

        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getCatalogs();

            while (resultSet.next()) {
                String dbName = resultSet.getString("TABLE_CAT");
                databaseNames.add(dbName);
            }
        }

        return databaseNames;
    }

    /**
     * Retrieves a list of table names in the current database.
     *
     * @return A list of table names.
     * @throws SQLException If a database access error occurs.
     */
    public static List<String> getTableNames() throws SQLException {
        List<String> table_names = new ArrayList<>();

        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(databaseName, null, "%", new String[]{"TABLE"});

            while (resultSet.next()) {
                String table_name = resultSet.getString("TABLE_NAME");
                table_names.add(table_name);
            }
        }

        return table_names;
    }

    /**
     * Maps a table name to its corresponding entity class.
     *
     * @param table_name The name of the table.
     * @return The corresponding entity class.
     */
    public static Class<?> getEntityClassForTableName(String table_name) {

        String className = "Clases." + table_name;

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Retrieves the JDBC URL template used for creating database connections.
     *
     * @return The JDBC URL template.
     */
    public static String getJdbc_url() {
        return jdbc_url;
    }

    /**
     * Retrieves the username used for connecting to the database.
     *
     * @return The database username.
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Retrieves the password used for connecting to the database.
     *
     * @return The database password.
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Retrieves the name of the currently selected database.
     *
     * @return The name of the database.
     */
    public static String getDatabaseName() {
        return databaseName;
    }

}
