package com.example.englishlearning.model;

import java.util.List;

public class ImageResponse {
    int total;
    List<Result> results;

    public ImageResponse() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
