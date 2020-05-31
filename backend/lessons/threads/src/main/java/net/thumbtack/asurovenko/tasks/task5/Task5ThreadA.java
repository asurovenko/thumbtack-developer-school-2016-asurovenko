package net.thumbtack.asurovenko.tasks.task5;

public class Task5ThreadA implements Runnable{
    public void run() {
        for (int i=0;i<10000;i++) {
            list.put(i);
        }
    }
    private MySinchronizedList list;
    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public Task5ThreadA(MySinchronizedList list) {
        this.list = list;
        thread = new Thread(this);
        thread.start();
    }
}
