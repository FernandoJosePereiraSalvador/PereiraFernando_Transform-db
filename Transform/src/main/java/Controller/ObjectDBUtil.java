/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility class for managing the ObjectDB EntityManagerFactory.
 *
 * This class provides methods for obtaining an EntityManager and closing the EntityManagerFactory.
 * It initializes the EntityManagerFactory with the specified persistence unit name.
 *
 * @author Fernando
 */
public class ObjectDBUtil {
    /**
     * The name of the persistence unit, specifying the location of the ObjectDB database file.
     */
    private static final String db_path = "C:\\Users\\Fernando\\Documents\\PereiraFernando_Transform-db\\objectdb-2.8.9\\db\\points40.odb";

    /**
     * The EntityManagerFactory instance used for creating EntityManagers.
     */
    private static EntityManagerFactory factory;

    /**
     * Static block to initialize the EntityManagerFactory when the class is loaded.
     */
    static {
        factory = Persistence.createEntityManagerFactory(db_path);
    }

    /**
     * Retrieves a new EntityManager instance.
     *
     * @return The EntityManager instance.
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    /**
     * Closes the EntityManagerFactory.
     */
    public static void closeEntityManager() {
        factory.close();
    }

    public static String getDb_path() {
        return db_path;
    }
    
    
}
