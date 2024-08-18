package table;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ConfusionMatrixModel extends AbstractTableModel {
    private final String[] columnNames;
    private final String[][] cells;

    public ConfusionMatrixModel(String[] columnNames, String[][] cells) {
        this.columnNames = columnNames;
        this.cells = cells;
    }

    @Override
    public int getRowCount() {
        return cells.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return cells[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int a) {
        return columnNames[a];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            int value = Integer.parseInt((String) aValue);
            cells[rowIndex][columnIndex] = String.valueOf(value);
            fireTableCellUpdated(rowIndex, columnIndex);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Incorrect value!");
        }
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public String[][] getCells() {
        return cells;
    }
}
