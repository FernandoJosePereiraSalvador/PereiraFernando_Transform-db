/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package View;

/**
 *
 * @author Fernando
 */
import Clases.DataTableModel;
import Controller.ConnectionDB;
import Controller.DAO;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteGUI extends JFrame {
    private JComboBox<String> databaseComboBox;
    private JComboBox<String> tableComboBox;
    private JTable dataTable;
    private JButton deleteButton;

    private EntityManager entityManager;

    public DeleteGUI() throws SQLException {
        super("Delete Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        initLayout();

        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() throws SQLException {
        databaseComboBox = new JComboBox<>();
        tableComboBox = new JComboBox<>();
        dataTable = new JTable();
        deleteButton = new JButton("Eliminar");

        // Cargar nombres de las bases de datos en el JComboBox
        List<String> databaseNames = ConnectionDB.getDatabaseNames();
        for (String dbName : databaseNames) {
            databaseComboBox.addItem(dbName);
        }

        // Configurar el evento de cambio de la base de datos seleccionada
        databaseComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableNames();
            }
        });

        // Configurar el evento del botón de eliminación
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRecord();
            }
        });

        // Inicializar el EntityManager
        initEntityManager();
    }

    private void initEntityManager() {
        try {
            entityManager = ConnectionDB.ObjectDBUtil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Seleccionar Base de Datos: "));
        topPanel.add(databaseComboBox);

        topPanel.add(new JLabel("Seleccionar Tabla: "));
        topPanel.add(tableComboBox);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);
        add(deleteButton, BorderLayout.SOUTH);
    }

    private void loadTableNames() {
        String selectedDatabase = (String) databaseComboBox.getSelectedItem();
        ConnectionDB.setDatabaseName(selectedDatabase);

        try {
            List<String> tableNames = ConnectionDB.getTableNames();
            tableComboBox.removeAllItems();
            for (String tableName : tableNames) {
                tableComboBox.addItem(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedRecord() {
        String selectedTableName = (String) tableComboBox.getSelectedItem();

        if (selectedTableName != null) {
            // Obtener la clase de entidad para la tabla seleccionada
            Class<?> entityClass = ConnectionDB.getEntityClassForTableName(selectedTableName);

            // Crear una instancia del GenericDAO para la entidad
            DAO<?> genericDAO = new DAO<>(entityManager, entityClass);

            // Obtener la fila seleccionada
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Obtener el identificador de la fila seleccionada
                Object idToDelete = dataTable.getValueAt(selectedRow, 0);

                // Eliminar la fila de la base de datos
                genericDAO.delete(idToDelete);

                // Recargar los datos de la tabla
                loadTableData(selectedTableName);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadTableData(String tableName) {
        // Crear una instancia del GenericDAO para la entidad correspondiente a la tabla
        Class<?> entityClass = ConnectionDB.getEntityClassForTableName(tableName);
        DAO<?> genericDAO = new DAO<>(entityManager, entityClass);
        // Obtener todos los datos de la tabla
        List<?> data = genericDAO.getAll();
        // Mostrar los datos en la tabla
        DataTableModel dataTableModel = new DataTableModel((List<Object[]>) data);
        dataTable.setModel(dataTableModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new DeleteGUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(DeleteGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
