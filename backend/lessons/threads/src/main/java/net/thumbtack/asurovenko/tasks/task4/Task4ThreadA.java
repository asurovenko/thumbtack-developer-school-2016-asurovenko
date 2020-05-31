package net.thumbtack.asurovenko.tasks.task4;

import java.util.ArrayList;
import java.util.Random;

public class Task4ThreadA implements Runnable {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (myList) {
                myList.put(i);
            }
        }
    }

    private MyList myList;

    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public Task4ThreadA(MyList myList) {
        this.myList = myList;
        thread = new Thread(this);
        thread.start();
    }
}
