package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.figures.Circle;
import net.thumbtack.asurovenko.trainee.figures.Rectangle;

public class PairBox<T extends Rectangle, E extends Circle> {
    private T object1;
    private E object2;

    public PairBox(T object1, E object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public T getObject1() {
        return object1;
    }

    public void setObject1(T object1) {
        this.object1 = object1;
    }

    public E getObject2() {
        return object2;
    }

    public void setObject2(E object2) {
        this.object2 = object2;
    }

    public boolean isSameSquare() {
        return Math.abs(object1.area() - object2.area()) < 0.000001;
    }
}
