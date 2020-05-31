package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Answers {

    @SerializedName("answers")
    @Expose
    private List<Integer> answers = null;

    public Answers() {
        answers = new ArrayList<>();
    }

    public Answers(List<Integer> answers) {
        this.answers = answers;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Integer answer) {
        answers.add(answer);
    }
}