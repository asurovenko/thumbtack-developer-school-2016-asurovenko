package net.thumbtack.asurovenko.tasks.task4;

/**
 * Created by thumbuser on 19.02.2017.
 */
public class Task4ThreadB implements Runnable {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (myList) {
                myList.randomRemove();
            }
        }
    }

    private MyList myList;

    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public Task4ThreadB(MyList myList) {
        this.myList = myList;
        thread = new Thread(this);
        thread.start();
    }
}
