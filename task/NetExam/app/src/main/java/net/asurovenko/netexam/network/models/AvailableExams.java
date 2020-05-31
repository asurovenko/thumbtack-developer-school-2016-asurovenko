package net.asurovenko.netexam.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableExams {

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
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("patronymic")
        @Expose
        private String patronymic;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("position")
        @Expose
        private String position;
        @SerializedName("questionsCountPerExam")
        @Expose
        private int questionsCountPerExam;
        @SerializedName("timeInMinutes")
        @Expose
        private int timeInMinutes;

        public Exam(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getDescription() {
            return new StringBuilder()
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

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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

}
