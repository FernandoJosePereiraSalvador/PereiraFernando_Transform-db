/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package View;

/**
 *
 * @author Fernando
 */
import Controller.ConnectionDB;
import Controller.Transform;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * The main graphical user interface (GUI) for the Database Transformer application.
 * Allows users to explore databases, select tables, and transform them using the Transformer program.
 * The GUI includes a JComboBox for selecting databases, a JList for displaying tables, and a JButton for initiating table transformation.
 *
 * @author Fernando
 */
public class TransformerGUI extends JFrame {
    private JComboBox<String> databaseComboBox;
    private JList<String> tableList;
    private JButton transformButton;
    
    /**
     * Constructs a new instance of TransformerGUI.
     * Sets up the GUI components, initializes layouts, and configures event listeners.
     */
    public TransformerGUI() {
        super("Database Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        initLayout();

        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Initializes GUI components, such as JComboBox, JList, and JButton.
     * Also configures event listeners for database selection and transformation button.
     */
    private void initComponents() {
        databaseComboBox = new JComboBox<>();
        tableList = new JList<>();
        transformButton = new JButton("Transformar Tablas Seleccionadas");

        // Cargar nombres de las bases de datos en el JComboBox
        try {
            List<String> databaseNames = ConnectionDB.getDatabaseNames();
            for (String dbName : databaseNames) {
                databaseComboBox.addItem(dbName);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }

        // Configurar el evento de cambio de la base de datos seleccionada
        databaseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableNames();
            }
        });

        // Configurar el evento del botón de transformación
        transformButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transformSelectedTables();
            }
        });
    }
    
    /**
     * Initializes the layout of the GUI, setting the layout manager and arranging components.
     */
    private void initLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Seleccionar Base de Datos: "));
        topPanel.add(databaseComboBox);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(tableList), BorderLayout.CENTER);
        add(transformButton, BorderLayout.SOUTH);
    }
    
    /**
     * Loads table names into the JList based on the selected database.
     */
    private void loadTableNames() {
        String selectedDatabase = (String) databaseComboBox.getSelectedItem();
        ConnectionDB.setDatabaseName(selectedDatabase);

        try {
            List<String> tableNames = ConnectionDB.getTableNames();
            tableList.setListData(tableNames.toArray(new String[0]));
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
    
    /**
     * Initiates the transformation process for the selected tables.
     */
    private void transformSelectedTables() {
        List<String> selectedTables = tableList.getSelectedValuesList();
        System.out.println("Tablas Seleccionadas: " + selectedTables);

        // Llamar al programa Transformer con las tablas seleccionadas como parámetros
        callTransformer(selectedTables);
    }
    
    /**
     * Invokes the Transformer program with the selected tables as parameters for transformation.
     *
     * @param selectedTables The list of selected tables for transformation.
     */
    private void callTransformer(List<String> selectedTables) {
        Transform transform = new Transform(ConnectionDB.getDatabaseName(),selectedTables);
        transform.executeTransformation("C:\\Users\\Fernando\\Documents\\PereiraFernando_Transform-db\\objectdb-2.8.9\\db\\points40.odb");
    }
    
    /**
     * The main entry point for the Database Transformer application.
     * Creates and displays an instance of the TransformerGUI.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TransformerGUI().setVisible(true);
            }
        });
    }
}
