package net.thumbtack.asurovenko.tasks.task16;

public class ProducerMyTask extends Thread {
    private TaskBuffer buffer;
    private int numberTask;

    public ProducerMyTask(String name, TaskBuffer taskBuffer) {
        numberTask = 0;
        this.setName(name);
        this.buffer = taskBuffer;
        this.start();
    }

    @Override
    public void run() {
        for (;;) {
            try {
                buffer.put(new MyTask(getName() + ": task" + (++numberTask)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
