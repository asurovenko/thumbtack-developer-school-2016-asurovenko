package net.thumbtack.asurovenko.tasks.task15;

import java.util.Arrays;
import java.util.Random;

public class Data {
    private int[] data;

    public Data(int[] data) {
        this.data = data;
    }

    public int[] get() {
        return data;
    }

    private static Random random = new Random();

    public static Data newRandomInstance() {
        int count = random.nextInt(10) + 1;
        int[] ints = new int[count];
        for (int i = 0; i < count; i++) {
            ints[i] = random.nextInt(100);
        }
        return new Data(ints);
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
