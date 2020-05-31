package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.asurovenko.netexam.ui.student_screen.NumberResults;

import java.util.List;

public class CompletedExams {

    @SerializedName("exams")
    @Expose
    private List<Exam> exams = null;

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public class Exam {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("teacher")
        @Expose
        private User teacher;
        @SerializedName("results")
        @Expose
        private List<String> results = null;

        private NumberResults numberResults = null;

        public NumberResults getNumberResults() {
            if (numberResults == null) {
                numberResults = new NumberResults(results);
            }
            return numberResults;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User getTeacher() {
            return teacher;
        }

        public void setTeacher(User teacher) {
            this.teacher = teacher;
        }

        public List<String> getResults() {
            return results;
        }

        public void setResults(List<String> results) {
            this.results = results;
        }
    }

}