package pl.edu.pwr.service1;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Kappa implements AnalysisService {
    private String[][] confusionMatrix;

    public static double calculateKappa(String[][] confusionMatrix) {
        int sum = Arrays.stream(confusionMatrix).flatMapToInt(row -> Arrays.stream(row).mapToInt(Integer::parseInt)).sum();
        int sumDiagonal = IntStream.range(0, confusionMatrix.length).map(i -> Integer.parseInt(confusionMatrix[i][i])).sum();
        int[] rowSums = Arrays.stream(confusionMatrix).mapToInt(row -> Arrays.stream(row).mapToInt(Integer::parseInt).sum()).toArray();
        int numClasses = confusionMatrix.length;
        double expectedAccuracy = IntStream.range(0, numClasses)
                .mapToDouble(i -> (double) rowSums[i] / sum)
                .map(rowSumRatio -> rowSumRatio * rowSumRatio)
                .sum();
        double observedAccuracy = (double) sumDiagonal / sum;
        return (observedAccuracy - expectedAccuracy) / (1 - expectedAccuracy);
    }


    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Kappa";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        confusionMatrix = ds.getData();
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        String[][] data = {{String.valueOf(calculateKappa(confusionMatrix))}};
        DataSet dataSet = new DataSet();
        dataSet.setData(data);
        return dataSet;
    }

    @Override
    public String toString() {
        return getName();
    }
}
