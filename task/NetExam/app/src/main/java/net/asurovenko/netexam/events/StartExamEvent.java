package net.asurovenko.netexam.events;


import net.asurovenko.netexam.network.models.AvailableExams;

public class StartExamEvent {
    private AvailableExams.Exam exam;

    public StartExamEvent(AvailableExams.Exam exam) {
        this.exam = exam;
    }

    public AvailableExams.Exam getExam() {
        return exam;
    }
}
