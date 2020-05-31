package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.Color;
import net.thumbtack.asurovenko.trainee.Figure;
import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import net.thumbtack.asurovenko.trainee.interfaces.Colored;

public class Circle extends Figure implements Colored {
    private Point2D center;
    private double radius;
    private Color color;

    public Circle(Point2D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Circle(double x, double y, double radius) {
        this(new Point2D(x, y), radius);
    }

    public Circle(double x, double y, double radius, String color) throws ColorException {
        this(x, y, radius);
        this.color = Color.chooseColor(color);
    }

    public void move(double dx, double dy) {
        center.move(dx, dy);
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public double length() {
        return 2 * Math.PI * radius;
    }

    public boolean pointContained(double x, double y) {
        if (((x - center.getX()) * (x - center.getX()) + (y - center.getY()) * (y - center.getY())) < radius * radius)
            return true;
        else return false;
    }

    public boolean pointContained(Point2D point) {
        return pointContained(point.getX(), point.getY());
    }

    public void paint(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String print() {
        return center.print() + "\nRadius = " + radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        if (Double.compare(circle.radius, radius) != 0) return false;
        if (center != null ? !center.equals(circle.center) : circle.center != null) return false;
        return color == circle.color;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = center != null ? center.hashCode() : 0;
        temp = Double.doubleToLongBits(radius);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
