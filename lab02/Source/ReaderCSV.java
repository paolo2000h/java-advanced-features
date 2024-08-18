package org.lab02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReaderCSV {

    private ReaderCSV() {
        throw new IllegalStateException("Utility class!");
    }
    public static List<List<String>> getRecords(String path) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    public static List<String> getHeaders(String path) {
        try (BufferedReader br = new BufferedReader((new FileReader(path)))) {
            String[] values = br.readLine().split(";");
            return Arrays.asList(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
