package com.elkana.food.models.results;

import com.elkana.food.models.results.Results;

import java.util.ArrayList;

public class ResultsResponse {

    //Properties:
    private ArrayList<Results> results;

    //Constructor (Empty):
    public ResultsResponse(){};

    //Getter + Setter:
    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "FoodsResponse{" +
                "results=" + results +
                '}';
    }
}
