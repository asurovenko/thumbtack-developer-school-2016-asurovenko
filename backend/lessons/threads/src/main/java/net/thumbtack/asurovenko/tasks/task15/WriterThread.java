package net.thumbtack.asurovenko.tasks.task15;

import java.util.concurrent.BlockingQueue;

public class WriterThread extends Thread {
    BlockingQueue<Data> queue;

    public WriterThread(BlockingQueue<Data> queue) {
        this.queue = queue;
        this.start();
    }

    @Override
    public void run() {
        for (; ; ) {
            queue.add(Data.newRandomInstance());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception");
            }
        }
    }
}
