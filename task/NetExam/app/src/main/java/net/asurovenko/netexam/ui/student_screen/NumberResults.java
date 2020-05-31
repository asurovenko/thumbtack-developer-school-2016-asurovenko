package net.asurovenko.netexam.ui.student_screen;


import java.util.List;

public class NumberResults {
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final String NO_ANSWER = "NO_ANSWER";

    private int correct;
    private int incorrect;
    private int noAnswer;

    public NumberResults(List<String> result) {
        correct = 0;
        incorrect = 0;
        noAnswer = 0;
        for (String s : result) {
            if (YES.equalsIgnoreCase(s)) {
                correct++;
                continue;
            }
            if (NO.equalsIgnoreCase(s)) {
                incorrect++;
                continue;
            }
            if (NO_ANSWER.equalsIgnoreCase(s)) {
                noAnswer++;
            }
        }
    }

    public int getCorrect() {
        return correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public int getNoAnswer() {
        return noAnswer;
    }

}
