package net.thumbtack.asurovenko.tasks.task11;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionalPingPong {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int stage = 0;

    public void ping() {
        try {
            lock.lock();
            if (stage == 1) {
                condition.await();
            }
            stage = 1;
            System.out.println("PING");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        condition.signal();
        lock.unlock();
    }

    public void pong() {
        try {
            lock.lock();
            if (stage == 2) {
                condition.await();
            }
            stage = 2;
            System.out.println("pong");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        condition.signal();
        lock.unlock();
    }
}
