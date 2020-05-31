package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamReadyForm {
    @SerializedName("questionsCountPerExam")
    @Expose
    private int questionsCountPerExam;
    @SerializedName("timeInMinutes")
    @Expose
    private int timeInMinutes;

    public ExamReadyForm(int questionsCountPerExam, int timeInMinutes) {
        this.questionsCountPerExam = questionsCountPerExam;
        this.timeInMinutes = timeInMinutes;
    }

    public int getQuestionsCountPerExam() {
        return questionsCountPerExam;
    }

    public void setQuestionsCountPerExam(int questionsCountPerExam) {
        this.questionsCountPerExam = questionsCountPerExam;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

}