package net.thumbtack.asurovenko.tasks.task8;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReaderWriterSystem {
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void write() {
        lock.writeLock().lock();
        try {
            System.out.println("START WRITE VALUE");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            System.out.println("FINISH WRITE VALUE");
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void read() {
        lock.readLock().lock();
        try {
            System.out.println("start read value");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            System.out.println("finish read value");
        } finally {
            lock.readLock().unlock();
        }
    }
}
