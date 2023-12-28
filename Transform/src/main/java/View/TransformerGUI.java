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
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TransformerGUI extends JFrame {
    private JComboBox<String> databaseComboBox;
    private JList<String> tableList;
    private JButton transformButton;

    public TransformerGUI() {
        super("Database Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        initLayout();

        pack();
        setLocationRelativeTo(null);
    }

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
            e.printStackTrace();
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

    private void initLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Seleccionar Base de Datos: "));
        topPanel.add(databaseComboBox);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(tableList), BorderLayout.CENTER);
        add(transformButton, BorderLayout.SOUTH);
    }

    private void loadTableNames() {
        String selectedDatabase = (String) databaseComboBox.getSelectedItem();
        ConnectionDB.setDatabaseName(selectedDatabase);

        try {
            List<String> tableNames = ConnectionDB.getTableNames();
            tableList.setListData(tableNames.toArray(new String[0]));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void transformSelectedTables() {
        List<String> selectedTables = tableList.getSelectedValuesList();
        System.out.println("Tablas Seleccionadas: " + selectedTables);

        // Llamar al programa Transformer con las tablas seleccionadas como parámetros
        invokeTransformer(selectedTables);
    }

    private void invokeTransformer(List<String> selectedTables) {
        Transform transform = new Transform(ConnectionDB.getDatabaseName(),selectedTables);
        transform.executeTransformation("C:\\Users\\Fernando\\Documents\\PereiraFernando_Transform-db\\objectdb-2.8.9\\db\\points40.odb");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TransformerGUI().setVisible(true);
            }
        });
    }
}
