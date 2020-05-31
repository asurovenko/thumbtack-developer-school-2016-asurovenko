package net.thumbtack.asurovenko.tasks.task5;

/**
 * Created by thumbuser on 19.02.2017.
 */
public class Task5ThreadB implements Runnable {
    public void run() {
        for (int i=0;i<10000;i++) {
            list.randomRemove();
        }
    }
    private MySinchronizedList list;
    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public Task5ThreadB(MySinchronizedList list) {
        this.list = list;
        thread = new Thread(this);
        thread.start();
    }
}
