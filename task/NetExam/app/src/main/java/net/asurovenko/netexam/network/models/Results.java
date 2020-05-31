package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.asurovenko.netexam.ui.student_screen.NumberResults;

import java.util.List;

public class Results {
    @SerializedName("results")
    @Expose
    private List<String> results = null;
    private NumberResults numberResults = null;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public NumberResults getNumberResults() {
        if (numberResults == null) {
            numberResults = new NumberResults(results);
        }
        return numberResults;
    }
}