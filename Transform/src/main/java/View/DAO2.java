/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package View;

import Clases.Astronauta;
import java.util.List;
import java.util.Scanner;
import java.util.List;
import java.util.Scanner;
import Controller.DAO;

/**
 *
 * @author Fernando
 */
public class DAO2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre de la entidad (clase): ");
        String entidadNombre = scanner.nextLine();

        // Reflection to get the entity class
        Class<?> entidadClass;
        try {
            entidadClass = Class.forName(entidadNombre);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar la clase de la entidad.");
            return;
        }

        DAO<?> dao = new DAO<>(entidadClass);

        int opcion;
        do {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear entidad");
            System.out.println("2. Leer entidad por clave primaria");
            System.out.println("3. Actualizar entidad");
            System.out.println("4. Eliminar entidad");
            System.out.println("5. Obtener todas las entidades");
            System.out.println("0. Salir");

            System.out.print("Ingrese el número de la opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    // Logic to create an entity
                    Object nuevaEntidad = crearEntidad(entidadClass);
                    //dao.create(nuevaEntidad);
                    System.out.println("Entidad creada exitosamente.");
                    break;
                case 2:
                    // Logic to read an entity by primary key
                    System.out.print("Ingrese la clave primaria: ");
                    Object primaryKey = scanner.nextLine();
                    Object entidadLeida = dao.read(primaryKey);
                    System.out.println("Entidad leída: " + entidadLeida);
                    break;
                case 3:
                    // Logic to update an entity
                    Object entidadActualizada = crearEntidad(entidadClass);
                    //dao.update(entidadActualizada);
                    System.out.println("Entidad actualizada exitosamente.");
                    break;
                case 4:
                    // Logic to delete an entity
                    System.out.print("Ingrese la clave primaria de la entidad a eliminar: ");
                    Object primaryKeyEliminar = scanner.nextLine();
                    dao.delete(primaryKeyEliminar);
                    System.out.println("Entidad eliminada exitosamente.");
                    break;
                case 5:
                    // Logic to get all entities
                    List<?> entidades = dao.getAll();
                    System.out.println("Listado de entidades:");
                    for (Object entidad : entidades) {
                        System.out.println(entidad);
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }

        } while (opcion != 0);
    }

    private static Object crearEntidad(Class<?> entidadClass) {
        try {
            return entidadClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Error al crear la instancia de la entidad.");
            return null;
        }
    }
}
