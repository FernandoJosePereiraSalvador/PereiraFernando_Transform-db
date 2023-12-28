/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controller;

import Clases.Astronauta;

/**
 *
 * @author Fernando
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Crear una instancia de GenericDAO para Astronauta
        DAO<Astronauta> astronautaDAO = new DAO<>(Astronauta.class);

        // Eliminar el astronauta por su ID
        astronautaDAO.delete(1);

        // Cerrar la conexión al finalizar
        ObjectDBUtil.closeEntityManager();
    }
    
}
