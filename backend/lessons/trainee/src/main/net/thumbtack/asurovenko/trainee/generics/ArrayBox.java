package net.thumbtack.asurovenko.trainee.generics;

import net.thumbtack.asurovenko.trainee.figures.Rectangle;

public class ArrayBox<T extends Rectangle> {
    private T[] arrayObject;

    public ArrayBox(T[] arrayObject) {
        this.arrayObject = arrayObject;
    }

    public T[] getArrayObject() {
        return arrayObject;
    }

    public void setArrayObject(T[] arrayObject) {
        this.arrayObject = arrayObject;
    }
}
