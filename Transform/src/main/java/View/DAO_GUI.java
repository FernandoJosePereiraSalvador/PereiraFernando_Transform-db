/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package View;

import Clases.Astronauta;
import Clases.Equipo;
import java.util.List;
import java.util.Scanner;
import Controller.DAO;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DAO GUI (Graphical User Interface) for interacting with a generic DAO.
 * Allows creating, reading, updating, and deleting entities.
 *
 * @author Fernando
 */
public class DAO_GUI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the entity name (class): ");
        String entityName = scanner.nextLine();

        // Reflection to get the entity class
        Class<?> entityClass;
        try {
            entityClass = Class.forName(entityName);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the entity class.");
            return;
        }

        DAO<?> dao = new DAO<>(entityClass);

        int option;
        do {
            System.out.println("Select an option:");
            System.out.println("1. Create entity");
            System.out.println("2. Read entity by primary key");
            System.out.println("3. Update entity");
            System.out.println("4. Delete entity");
            System.out.println("5. Get all entities");
            System.out.println("0. Exit");

            System.out.print("Enter the option number: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // Logic to create an entity
                    Astronauta newAstronaut = new Astronauta(1, "John", "Doe", new Date(), new Date(), "Male",
                            "American", 2, BigDecimal.valueOf(100), new Equipo(), 1);
                    DAO<Astronauta> daoCreate = new DAO<>(Astronauta.class);
                    daoCreate.create(newAstronaut);
                    System.out.println("Entity created successfully.");
                    break;
                case 2:
                    // Logic to read an entity by primary key
                    System.out.print("Enter the primary key: ");
                    Object primaryKey = scanner.nextLine();
                    Object readEntity = dao.read(primaryKey);
                    System.out.println("Entity read: " + readEntity);
                    break;
                case 3:
                    // Logic to update an entity
                    Astronauta updatedAstronaut = new Astronauta(1, "John", "Doe", new Date(), new Date(), "Male",
                            "American", 2, BigDecimal.valueOf(100), new Equipo(), 1);
                    DAO<Astronauta> daoUpdate = new DAO<>(Astronauta.class);
                    daoUpdate.update(updatedAstronaut);
                    System.out.println("Entity updated successfully.");
                    break;
                case 4:
                    // Logic to delete an entity
                    System.out.print("Enter the primary key of the entity to delete: ");
                    Object primaryKeyToDelete = scanner.nextLine();
                    dao.delete(primaryKeyToDelete);
                    System.out.println("Entity deleted successfully.");
                    break;
                case 5:
                    // Logic to get all entities
                    List<?> entities = dao.getAll();
                    System.out.println("List of entities:");
                    for (Object entity : entities) {
                        System.out.println(entity);
                    }
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }

        } while (option != 0);
    }

    private static Object createEntity(Class<?> entityClass) {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Error creating an instance of the entity.");
            return null;
        }
    }
}
