package net.thumbtack.asurovenko.tasks.task3;

/**
 * Created by thumbuser on 19.02.2017.
 */
public class Task3Thread implements Runnable {
    public void run() {
        System.out.println("Start " + name);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println("Complete " + name);
    }

    private String name;

    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public Task3Thread(String name) {
        this.name = name;
        thread = new Thread(this);
        thread.start();
    }
}
