package pl.edu.pwr.service2;

import com.google.auto.service.AutoService;
import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

import java.util.Arrays;
import java.util.stream.IntStream;

@AutoService(AnalysisService.class)
public class MeanRecall implements AnalysisService {
    private String[][] confusionMatrix;

    public static double calculateMeanRecall(String[][] confusionMatrix) {
        int numClasses = confusionMatrix.length;
        double[] recall = IntStream.range(0, numClasses)
                .mapToDouble(i -> {
                    int rowSum = Arrays.stream(confusionMatrix[i]).mapToInt(Integer::parseInt).sum();
                    return (double) Integer.parseInt(confusionMatrix[i][i]) / rowSum;
                })
                .toArray();
        return Arrays.stream(recall).average().orElse(Double.NaN);
    }

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Mean recall";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        confusionMatrix = ds.getData();
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        String[][] data = {{String.valueOf(calculateMeanRecall(confusionMatrix))}};
        DataSet dataSet = new DataSet();
        dataSet.setData(data);
        return dataSet;
    }

    @Override
    public String toString() {
        return getName();
    }

}
