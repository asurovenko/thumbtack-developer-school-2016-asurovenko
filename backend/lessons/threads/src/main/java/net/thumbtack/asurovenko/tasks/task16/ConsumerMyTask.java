package net.thumbtack.asurovenko.tasks.task16;

public class ConsumerMyTask extends Thread {
    private TaskBuffer buffer;

    public ConsumerMyTask(TaskBuffer taskBuffer) {
        this.buffer = taskBuffer;
        this.start();
    }

    @Override
    public void run() {
        for (;;) {
            try {
                buffer.take().execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
