/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Fernando
 */
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DataTableModel extends AbstractTableModel {
    private List<Object[]> data;  // Ajusta el tipo de datos según tus necesidades
    private String[] columnNames;

    public DataTableModel(List<Object[]> data) {
        this.data = data;
        // Asumo que los nombres de las columnas no cambiarán, pero puedes adaptarlo según tus necesidades
        this.columnNames = new String[0];  
    }

    public DataTableModel(List<Object[]> data, String[] columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
