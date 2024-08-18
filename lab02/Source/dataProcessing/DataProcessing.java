package org.lab02.dataProcessing;

import java.util.List;

public interface DataProcessing<T> {
    public  T[] getResult(List<List<T>> data, String[] columnNames);
    public String getDataProcessingName();
}
