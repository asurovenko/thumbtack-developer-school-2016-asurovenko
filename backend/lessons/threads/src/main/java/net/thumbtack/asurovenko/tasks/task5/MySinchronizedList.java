package net.thumbtack.asurovenko.tasks.task5;

import java.util.ArrayList;

/**
 * Created by thumbuser on 19.02.2017.
 */
public class MySinchronizedList {
    private ArrayList<Integer> list;

    public MySinchronizedList() {
        this.list = new ArrayList<Integer>();
    }

    public synchronized void put(int value) {
        System.out.println("Put "+value);
        list.add(value);
    }

    public synchronized void randomRemove() {
        if (!list.isEmpty()) {
            System.out.println("Removed");
            list.remove((int) (Math.random() * list.size()));
        } else {
            System.out.println("Empty list");
        }
    }
}
