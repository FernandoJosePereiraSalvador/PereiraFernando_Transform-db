/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.transform;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Fernando
 */
@Entity
public class Point implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    public long id;
    
    private int x;
    private int y;
    
    public Point(){
        
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" + "id=" + id + ", x=" + x + ", y=" + y + '}';
    }
       
    
    
}
