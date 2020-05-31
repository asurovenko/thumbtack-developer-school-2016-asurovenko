package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.figures.Rectangle;
import net.thumbtack.asurovenko.trainee.generics.ArrayBox;

public class NamedArrayBox<T extends Rectangle> extends ArrayBox<T> {
    private String name;

    public NamedArrayBox(T[] arrayObject, String name) {
        super(arrayObject);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
