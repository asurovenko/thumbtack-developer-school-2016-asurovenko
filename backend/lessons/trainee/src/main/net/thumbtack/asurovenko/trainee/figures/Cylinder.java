package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.Color;
import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import net.thumbtack.asurovenko.trainee.interfaces.Colored;

public class Cylinder extends Circle implements Colored {
    private double height;
    private Color color;

    public Cylinder(double x, double y, double radius, double height) {
        super(x, y, radius);
        this.height = height;
    }

    public Cylinder(double x, double y, double radius, double height, String color) throws ColorException {
        this(x, y, radius, height);
        this.color = Color.chooseColor(color);
    }

    public Cylinder(Point2D center, double radius, double height) {
        this(center.getX(), center.getY(), radius, height);
    }

    public double volume() {
        return super.area() * height;
    }

    public double areaSide() {
        return super.length() * height;
    }

    public boolean pointContained(double x, double y, double z) {
        return super.pointContained(x, y) && height > z;
    }

    public boolean pointContained(Point3D p) {
        return pointContained(p.getX(), p.getY(), p.getZ());
    }

    public void paint(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String print() {
        return getCenter().print() + "\nRadius = " + getRadius() + "\nHeight = " + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        if (Double.compare(cylinder.height, height) != 0) return false;
        return color == cylinder.color;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
