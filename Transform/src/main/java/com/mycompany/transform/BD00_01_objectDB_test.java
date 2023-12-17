/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.transform;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Fernando
 */
public class BD00_01_objectDB_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("C:\\Users\\Fernando\\Documents\\PereiraFernando_Transform-db\\objectdb-2.8.9\\db\\points.odb");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }

        em.getTransaction().commit();

        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");

        System.out.println("Total de puntos: " + q1.getSingleResult());

        // 2
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        System.out.println("Promedio de valores X: " + q2.getSingleResult());

        // 3
        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);

        List<Point> results = query.getResultList();
        System.out.println("\nPuntos (x,y) : \n");
        int k = 0;
        for (Point p : results) {
            System.out.println("ID=" + p + ": " + p + "");
            k++;
            if (k > 10) {
                System.out.println("");
            }
        }

        Point buscame = new Point();
        buscame.id = 10;

        buscame = em.find(Point.class, buscame);

        if (buscame != null) {
            System.out.println("\n\n --------Encontro con ID = 10");
        } else {
            System.out.println("  ------ no existe el punto con ID = 10");
        }

        em.close();

        emf.close();
    }

}
