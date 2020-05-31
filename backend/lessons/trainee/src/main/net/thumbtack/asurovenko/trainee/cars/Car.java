package net.thumbtack.asurovenko.trainee.cars;

import net.thumbtack.asurovenko.trainee.Color;
import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import net.thumbtack.asurovenko.trainee.interfaces.Colored;

public class Car implements Colored {
    private String mark;
    private int mass, speed;
    private Color color;

    public Car(String mark, int mass, int speed) {
        this.mark = mark;
        this.mass = mass;
        this.speed = speed;
    }

    public Car(String mark, int mass, int speed, String color) throws ColorException {
        this(mark, mass, speed);
        this.color = Color.chooseColor(color);
    }

    public void paint(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String print() {
        return "Mark: " + mark + "\nMass: " + mass + "\nSpeed: " + speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        if (mass != car.mass) return false;
        if (speed != car.speed) return false;
        if (mark != null ? !mark.equals(car.mark) : car.mark != null) return false;
        return color == car.color;
    }

    @Override
    public int hashCode() {
        int result = mark != null ? mark.hashCode() : 0;
        result = 31 * result + mass;
        result = 31 * result + speed;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
