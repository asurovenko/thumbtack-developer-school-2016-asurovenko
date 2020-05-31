package net.thumbtack.asurovenko.tasks.task15;

import java.util.concurrent.BlockingQueue;

public class ReaderThread extends Thread {
    BlockingQueue<Data> queue;

    public ReaderThread(BlockingQueue<Data> queue) {
        this.queue = queue;
        this.start();
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception");
            }
        }
    }
}
