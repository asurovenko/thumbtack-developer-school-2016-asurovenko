package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ExamResults {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("groups")
    @Expose
    private Map<String, Group> groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, Group> groups) {
        this.groups = groups;
    }

    public class Group {
        @SerializedName("students")
        @Expose
        private List<Student> students = null;

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
        }
    }


    public class Student {
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("patronymic")
        @Expose
        private String patronymic;
        @SerializedName("correct")
        @Expose
        private Integer correct;
        @SerializedName("wrong")
        @Expose
        private Integer wrong;
        @SerializedName("noAnswer")
        @Expose
        private Integer noAnswer;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public Integer getCorrect() {
            return correct;
        }

        public void setCorrect(Integer correct) {
            this.correct = correct;
        }

        public Integer getWrong() {
            return wrong;
        }

        public void setWrong(Integer wrong) {
            this.wrong = wrong;
        }

        public Integer getNoAnswer() {
            return noAnswer;
        }

        public void setNoAnswer(Integer noAnswer) {
            this.noAnswer = noAnswer;
        }
    }
}