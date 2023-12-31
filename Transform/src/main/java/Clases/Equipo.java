package Clases;

// default package
// Generated 17 dic 2023, 17:10:06 by Hibernate Tools 5.2.13.Final
import Controller.ConnectionDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Equipo generated by hbm2java
 */
@Entity
@Table(name = "equipo", catalog = "nasa_db")
public class Equipo implements java.io.Serializable {

    @Id
    @Column(name = "ID_Equipo")
    public int idEquipo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Mision")
    public Mision ID_Mision;
    @Column(name = "Nombre_Equipo")
    public String nombreEquipo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipo")
    public Set<Astronauta> astronautas = new HashSet<Astronauta>(0);
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "equipos")
    public Set<Nave> naves = new HashSet<Nave>(0);
    
    public Equipo() {
    }

    public Equipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Equipo(int idEquipo, Mision mision, String nombreEquipo, Set<Astronauta> astronautas, Set<Nave> naves) {
        this.idEquipo = idEquipo;
        this.ID_Mision = mision;
        this.nombreEquipo = nombreEquipo;
        this.astronautas = astronautas;
        this.naves = naves;
    }

    public Equipo(List<Object> valores) {
        
        System.out.println(valores.toString());
        this.idEquipo = (int) valores.get(0);
        this.nombreEquipo = (String) valores.get(1);
        this.setMision((int) valores.get(2));
        this.setAstronautas();
        this.setNaves();
    }

    public int getIdEquipo() {
        return this.idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Mision getMision() {
        return this.ID_Mision;
    }

    public void setMision(int id_mision) {
        Mision mision = null;

        try (Connection connection = ConnectionDB.getConnection()) {
            String query = "SELECT m.* FROM Mision m JOIN Equipo e ON m.ID_Mision = e.ID_Mision WHERE e.ID_Equipo = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id_mision);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        mision = new Mision();
                        mision.setIdMision(resultSet.getInt("ID_Mision"));
                        mision.setNombreMision(resultSet.getString("Nombre_Mision"));
                        mision.setFechaLanzamiento(resultSet.getDate("Fecha_Lanzamiento"));
                        mision.setFechaFinalizacion(resultSet.getDate("Fecha_Finalizacion"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error 36: " + e);
            
        }

        this.ID_Mision = mision;
    }

    public String getNombreEquipo() {
        return this.nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public Set<Astronauta> getAstronautas() {
        return this.astronautas;
    }

    public void setAstronautas() {
        Set<Astronauta> astronautas = new HashSet<>();

        try (Connection connection = ConnectionDB.getConnection()) {
            String query = "SELECT a.* FROM Astronauta a WHERE a.ID_Equipo = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.getIdEquipo());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Astronauta astronauta = new Astronauta();
                        astronauta.setID_Astronauta(resultSet.getInt("ID_Astronauta"));
                        astronauta.setNombre(resultSet.getString("Nombre"));
                        astronauta.setApellidos(resultSet.getString("Apellidos"));
                        // Otras propiedades del astronauta
                        astronautas.add(astronauta);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error 37: " + e);
        }

        this.astronautas = astronautas;
    }

    public Set<Nave> getNaves() {
        return this.naves;
    }

    public void setNaves() {
        Set<Nave> naves = new HashSet<>();

        try (Connection connection = ConnectionDB.getConnection()) {
            String query = "SELECT n.* FROM Nave n JOIN Nave_Equipo ne ON n.ID_Nave = ne.ID_Nave WHERE ne.ID_Equipo = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.getIdEquipo());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Nave nave = new Nave();
                        nave.setIdNave(resultSet.getInt("ID_Nave"));
                        nave.setNombreNave(resultSet.getString("Nombre_Nave"));
                        // Otras propiedades de la nave
                        naves.add(nave);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error 38: " + e);
        }

        this.naves = naves;
    }
    
    

    @Override
    public String toString() {
        return "Equipo{" + "ID_Equipo=" + idEquipo + ", mision=" + ID_Mision + ", nombreEquipo=" + nombreEquipo + ", astronautas=" + astronautas + ", naves=" + naves + '}';
    }

}
