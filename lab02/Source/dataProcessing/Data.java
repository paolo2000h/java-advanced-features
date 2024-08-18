package org.lab02.dataProcessing;

import java.util.List;

public class Data {
    private List<List<String>> records;
    private String[] headers;
    private String[] results;

    public void setList(List<List<String>> records, String[] headers, String[] results) {
        this.records = records;
        this.headers = headers;
        this.results = results;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public String[] getHeaders() {
        return headers;
    }

    public String[] getResults() {
        return results;
    }
}
