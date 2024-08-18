package org.lab02.table;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;

public class ResultTableModel extends AbstractTableModel {
    private final String[] columns;
    private final String[] results;

    public ResultTableModel(String[] columns, String[] results) {
        this.columns = Arrays.copyOfRange(columns, 1, columns.length);
        this.results = results;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return results[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
