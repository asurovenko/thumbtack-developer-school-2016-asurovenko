package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exam {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("semester")
    @Expose
    private int semester;
    @SerializedName("ready")
    @Expose
    private boolean ready;
    @SerializedName("questionsCountPerExam")
    @Expose
    private int questionsCountPerExam;
    @SerializedName("timeInMinutes")
    @Expose
    private int timeInMinutes;

    public Exam(int id, String name, int semester) {
        this.id = id;
        this.name = name;
        this.semester = semester;
    }

    public String getDescription() {
        return new StringBuilder("Готов. ")
                .append(questionsCountPerExam)
                .append(getWriting(questionsCountPerExam, " вопросов, ", " вопрос, ", " вопроса, "))
                .append(timeInMinutes)
                .append(getWriting(timeInMinutes, " минут", " минута", " минуты"))
                .toString();
    }

    private String getWriting(int count, String var1, String var2, String var3) {
        int mod100 = count % 100;
        if (mod100 >= 10 && mod100 <= 20) {
            return var1;
        } else {
            switch (count % 10) {
                case 1: {
                    return var2;
                }
                case 2:
                case 3:
                case 4: {
                    return var3;
                }
                default: {
                    return var1;
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
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