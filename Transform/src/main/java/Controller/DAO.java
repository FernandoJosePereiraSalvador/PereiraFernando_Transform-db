/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Fernando
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Generic Data Access Object (DAO) for performing CRUD operations on entities.
 *
 * @author Fernando
 * @param <T> Type of the entity.
 */
public class DAO<T> {

    private final Class<T> entityClass;

    /**
     * Constructor to create a DAO for a specific entity class.
     *
     * @param entityClass The class of the entity.
     */
    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Creates a new entity in the database.
     *
     * @param entity The entity to be created.
     */
    public void create(T entity) {
        EntityManager entityManager = ObjectDBUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println(e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Reads an entity from the database based on its primary key.
     *
     * @param primaryKey The primary key of the entity.
     * @return The retrieved entity.
     */
    public T read(Object primaryKey) {
        EntityManager entityManager = ObjectDBUtil.getEntityManager();
        T entity = entityManager.find(entityClass, primaryKey);
        entityManager.close();
        return entity;
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity to be updated.
     */
    public void update(T entity) {
        EntityManager entityManager = ObjectDBUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Deletes an entity from the database based on its primary key.
     *
     * @param primaryKey The primary key of the entity to be deleted.
     */
    public void delete(Object primaryKey) {
        EntityManager entityManager = ObjectDBUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            T entity = entityManager.find(entityClass, primaryKey);
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Retrieves all entities of the specified type from the database.
     *
     * @return A list of all entities in the database.
     */
    public List<T> getAll() {
        EntityManager entityManager = ObjectDBUtil.getEntityManager();
        List<T> list = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
        entityManager.close();
        return list;
    }
}
