package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Question {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("answers")
    @Expose
    private List<String> answers = null;
    @SerializedName("correct")
    @Expose
    private int correct;

    public Question() {
        question = "";
        answers = new ArrayList<>();
        correct = -1;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

}