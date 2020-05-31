package net.asurovenko.netexam.events;


import net.asurovenko.netexam.network.models.Exam;

public class OpenExamQuestionsEvent {
    private Exam exam;

    public OpenExamQuestionsEvent(Exam exam) {
        this.exam = exam;
    }

    public Exam getExam() {
        return exam;
    }
}