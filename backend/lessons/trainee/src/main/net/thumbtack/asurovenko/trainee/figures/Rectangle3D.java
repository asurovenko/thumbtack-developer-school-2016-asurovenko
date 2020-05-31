package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.Color;
import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import net.thumbtack.asurovenko.trainee.interfaces.Colored;

//REVU: extract "Figure" interface
public class Rectangle3D extends Rectangle implements Colored {
    private double height;
    private Color color;

    public Rectangle3D(double x1, double y1, double x2, double y2, double height) {
        super(x1, y1, x2, y2);
        this.height = height;
    }

    public Rectangle3D(double x1, double y1, double x2, double y2, double height, String color) throws ColorException {
        this(x1, y1, x2, y2, height);
        this.color = Color.chooseColor(color);
    }

    public Rectangle3D(Point2D p1, Point2D p2, double height) {
        super(p1, p2);
        this.height = height;
    }

    public Rectangle3D(double x, double y, double z) {
        this(0, 0, x, y, z);
    }

    public Rectangle3D() {
        this(1, 1, 1);
    }

    public void shrink(double ratio) {
        height /= ratio;
    }

    public double volume() {
        return super.area() * height;
    }

    public boolean pointContained(double x, double y, double z) {
        return super.pointContained(x, y) && height > z;
    }

    public boolean pointContained(Point3D p) {
        return pointContained(p.getX(), p.getY(), p.getZ());
    }

    public boolean rectangleContained(Rectangle3D rectangle) {
        return super.pointContained(rectangle.getFirst()) &&
                super.pointContained(rectangle.getSecond()) &&
                height > rectangle.getHeight();
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
        return getFirst().print() + ":0.0\n"
                + printRightBottomPoint() + ":0.0\n"
                + getSecond().print() + ":0.0\n"
                + printLeftTopPoint() + ":0.0\n"
                + getFirst().print() + ":" + height + "\n"
                + printRightBottomPoint() + ":" + height + "\n"
                + getSecond().print() + ":" + height + "\n"
                + printLeftTopPoint() + ":" + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle3D that = (Rectangle3D) o;
        if (Double.compare(that.height, height) != 0) return false;
        return color == that.color;
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
