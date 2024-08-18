package org.lab02.table;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MeasurementTableModel extends AbstractTableModel {
    private final String[] columnNames;
    private final List<List<String>> cells;

    public MeasurementTableModel(String[] columnNames, List<List<String>> cells) {
        this.columnNames = columnNames;
        this.cells = cells;
    }

    @Override
    public int getRowCount() {
        return cells.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return cells.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int a) {
        return columnNames[a];
    }
}
