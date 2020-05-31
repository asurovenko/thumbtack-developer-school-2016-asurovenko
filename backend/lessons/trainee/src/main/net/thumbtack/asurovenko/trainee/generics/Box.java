package net.thumbtack.asurovenko.trainee.generics;

import net.thumbtack.asurovenko.trainee.figures.Rectangle;
import net.thumbtack.asurovenko.trainee.interfaces.Square;

public class Box<T extends Rectangle> implements Square {
    private T object;

    public Box(T obj) {
        object = obj;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public double square() {
        return object.area();
    }

    public boolean isSameSquare(Box obj) {
        return (Math.abs(this.square() - obj.square()) < 0.000001);
    }
}
