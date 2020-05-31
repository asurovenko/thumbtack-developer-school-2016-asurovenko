package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddedExam {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("semester")
    @Expose
    private int semester;

    public AddedExam(String name, int semester) {
        this.name = name;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

}