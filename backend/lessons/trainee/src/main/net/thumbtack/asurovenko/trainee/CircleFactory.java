package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.figures.Circle;

import java.util.Random;

public class CircleFactory {
    private static Random random = new Random();
    private int count;

    public Circle createCircle() {
        count++;
        return new Circle(random.nextInt(10), random.nextInt(10), random.nextInt(10));
    }

    public int getCount() {
        return count;
    }
}
