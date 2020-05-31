package net.thumbtack.asurovenko.tasks.task13;

import java.util.Date;

public class FormatterThread implements Runnable {

    private Formatter formatter;
    private String nameThread;

    public FormatterThread(Formatter formatter, String nameThread) {
        this.formatter = formatter;
        this.nameThread=nameThread;
        new Thread(this).start();
    }

    public void run() {
        System.out.println(nameThread + "     " + formatter.format(new Date()));
    }
}
