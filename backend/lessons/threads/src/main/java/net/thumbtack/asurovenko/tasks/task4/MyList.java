package net.thumbtack.asurovenko.tasks.task4;

import java.util.ArrayList;

public class MyList {
    private ArrayList<Integer> list;

    public MyList() {
        list = new ArrayList<Integer>();
    }

    public void put(int value) {
        System.out.println("Put:" + value);
        list.add(value);
    }

    public void randomRemove() {
        if (!list.isEmpty()) {
            System.out.println("Removed");
            list.remove((int) (Math.random() * list.size()));
        } else {
            System.out.println("Empty list");
        }
    }
}
