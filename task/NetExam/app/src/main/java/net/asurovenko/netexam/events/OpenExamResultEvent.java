package net.asurovenko.netexam.events;


import net.asurovenko.netexam.network.models.Exam;

public class OpenExamResultEvent {
    private Exam exam;

    public OpenExamResultEvent(Exam exam) {
        this.exam = exam;
    }

    public Exam getExam() {
        return exam;
    }
}
