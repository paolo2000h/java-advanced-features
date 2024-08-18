package pl.edu.pwr.service2;

import com.google.auto.service.AutoService;
import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

import java.util.Arrays;
import java.util.stream.IntStream;

@AutoService(AnalysisService.class)
public class MeanPrecision implements AnalysisService {
    private String[][] confusionMatrix;

    public static double calculateMeanPrecision(String[][] confusionMatrix) {
        int numClasses = confusionMatrix.length;
        double[] precision = IntStream.range(0, numClasses)
                .mapToDouble(j -> {
                    int colSum = Arrays.stream(confusionMatrix).mapToInt(row -> Integer.parseInt(row[j])).sum();
                    return (double) Integer.parseInt(confusionMatrix[j][j]) / colSum;
                })
                .toArray();
        return Arrays.stream(precision).average().orElse(Double.NaN);
    }

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Mean precision";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        confusionMatrix = ds.getData();
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        String[][] data = {{String.valueOf(calculateMeanPrecision(confusionMatrix))}};
        DataSet dataSet = new DataSet();
        dataSet.setData(data);
        return dataSet;
    }

    @Override
    public String toString() {
        return getName();
    }
}
