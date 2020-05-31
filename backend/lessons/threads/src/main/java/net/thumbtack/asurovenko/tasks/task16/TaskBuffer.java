package net.thumbtack.asurovenko.tasks.task16;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskBuffer {
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    final private int BUFFER_SIZE = 100;
    final private MyTask[] ITEMS = new MyTask[BUFFER_SIZE];
    private  int putIndex, takeIndex, count;

    public void put(MyTask x) throws InterruptedException {
        lock.lock();
        try {
            while (count == BUFFER_SIZE)
                notFull.await();
            putIntoBuffer(x);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private void putIntoBuffer(MyTask x) {
        ITEMS[putIndex] = x;
        if (++putIndex == BUFFER_SIZE)
            putIndex = 0;
        ++count;
    }

    public MyTask take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            MyTask x = getFromBuffer();
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    private MyTask getFromBuffer() {
        MyTask x = ITEMS[takeIndex];
        if (++takeIndex == BUFFER_SIZE)
            takeIndex = 0;
        --count;
        return x;
    }
}
