package pl.edu.pwr.service3;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Accuracy implements AnalysisService {
    private String[][] confusionMatrix;

    public static double calculateAccuracy(String[][] confusionMatrix) {
        int sum = Arrays.stream(confusionMatrix).flatMapToInt(row -> Arrays.stream(row).mapToInt(Integer::parseInt)).sum();
        int sumDiagonal = IntStream.range(0, confusionMatrix.length).map(i -> Integer.parseInt(confusionMatrix[i][i])).sum();
        return (double) sumDiagonal / sum;
    }

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Accuracy";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        confusionMatrix = ds.getData();
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        String[][] data = {{String.valueOf(calculateAccuracy(confusionMatrix))}};
        DataSet dataSet = new DataSet();
        dataSet.setData(data);
        return dataSet;
    }

    @Override
    public String toString() {
        return getName();
    }
}
