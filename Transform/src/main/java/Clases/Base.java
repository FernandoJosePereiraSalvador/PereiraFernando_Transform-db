package Clases;

// default package
// Generated 17 dic 2023, 17:10:06 by Hibernate Tools 5.2.13.Final

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Base generated by hbm2java
 */
@Entity
@Table(name = "Base")
public class Base implements Serializable {

    @Id
    @Column(name = "ID_Base")
    private int idBase;

    @Column(name = "Localizacion")
    private String localizacion;

    @Column(name = "Nombre_Base")
    private String nombreBase;

    public Base(int idBase, String localizacion, String nombreBase) {
        this.idBase = idBase;
        this.localizacion = localizacion;
        this.nombreBase = nombreBase;
    }

    public Base() {

    }

    @Override
    public String toString() {
        return "Base{" + "idBase=" + idBase + ", localizacion=" + localizacion + ", nombreBase=" + nombreBase + '}';
    }

    public int getIdBase() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getNombreBase() {
        return nombreBase;
    }

    public void setNombreBase(String nombreBase) {
        this.nombreBase = nombreBase;
    }

}
