package net.thumbtack.asurovenko.tasks.task13;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
    public String format(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss").format(date);
    }
}
