package net.thumbtack.asurovenko.tasks.task10;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MyReentrantLockList {
    private final ReentrantLock lock = new ReentrantLock();
    private ArrayList<Integer> list;

    public MyReentrantLockList() {
        list = new ArrayList<Integer>();
    }

    public void put(int value) {
        lock.lock();
        System.out.println("Put " + value);
        list.add(value);
        lock.unlock();
    }

    public void randomRemove() {
        lock.lock();
        if (!list.isEmpty()) {
            System.out.println("Removed");
            list.remove((int) (Math.random() * list.size()));
        } else {
            System.out.println("Empty list");
        }
        lock.unlock();
    }
}
