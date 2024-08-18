package org.lab02.dataProcessing;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArithmeticAverage implements DataProcessing<String> {
    public static final String DATA_PROCESSING_NAME = "Arithmetic average";
    @Override
    public String[] getResult(List<List<String>> data, String[] columnNames) {
        List<List<Double>> numbers = data.stream().map(list -> list.stream().skip(1).map(Double::valueOf).collect(Collectors.toList())).toList();
        double[] averages = IntStream.range(0, numbers.get(0).size())
                .mapToDouble(i -> numbers.stream()
                        .mapToDouble(row -> row.get(i))
                        .average()
                        .orElse(0.0))
                .toArray();

        Stream<String> stringStream = Arrays.stream(averages).mapToObj(Double::toString);

        return stringStream.collect(Collectors.joining(", ")).split(", ");
    }

    @Override
    public String getDataProcessingName() {
        return DATA_PROCESSING_NAME;
    }
}
