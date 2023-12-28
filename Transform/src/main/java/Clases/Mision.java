package Clases;

// default package
// Generated 17 dic 2023, 17:10:06 by Hibernate Tools 5.2.13.Final

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mision generated by hbm2java
 */
@Entity
@Table(name = "Mision", catalog = "nasa_db")
public class Mision implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Mision")
    private int idMision;

    @Column(name = "Nombre_Mision")
    private String nombreMision;

    @Column(name = "Fecha_Lanzamiento")
    private Date fechaLanzamiento;

    @Column(name = "Fecha_Finalizacion")
    private Date fechaFinalizacion;

    public Mision(int idMision, String nombreMision, Date fechaLanzamiento, Date fechaFinalizacion) {
        this.idMision = idMision;
        this.nombreMision = nombreMision;
        this.fechaLanzamiento = fechaLanzamiento;
        this.fechaFinalizacion = fechaFinalizacion;
    }
    
    public Mision(List<Object> values) {
        System.out.println("Estos son los valores: " + values.toString());
        this.idMision = (int) values.get(0);
        this.nombreMision = (String) values.get(1);
        this.fechaLanzamiento = (Date) values.get(2);
        this.fechaFinalizacion = (Date) values.get(3);
    }
    
    public Mision(){
        
    }

    @Override
    public String toString() {
        return "Mision{" + "idMision=" + idMision + ", nombreMision=" + nombreMision + ", fechaLanzamiento=" + fechaLanzamiento + ", fechaFinalizacion=" + fechaFinalizacion + '}';
    }
    

    public int getIdMision() {
        return idMision;
    }

    public void setIdMision(int idMision) {
        this.idMision = idMision;
    }

    public String getNombreMision() {
        return nombreMision;
    }

    public void setNombreMision(String nombreMision) {
        this.nombreMision = nombreMision;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
    
    
    
    
}