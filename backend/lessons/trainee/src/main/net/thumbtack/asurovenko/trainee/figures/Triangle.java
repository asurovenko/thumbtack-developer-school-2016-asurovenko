package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.Color;
import net.thumbtack.asurovenko.trainee.Figure;
import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import net.thumbtack.asurovenko.trainee.interfaces.Colored;

public class Triangle extends Figure implements Colored {
    private Point2D point1, point2, point3;
    private Color color;

    public Triangle(Point2D point1, Point2D point2, Point2D point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this(new Point2D(x1, y1), new Point2D(x2, y2), new Point2D(x3, y3));
    }

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3, String color) throws ColorException {
        this(x1, y1, x2, y2, x3, y3);
        this.color = Color.chooseColor(color);
    }

    public void move(double dx, double dy) {
        point1.move(dx, dy);
        point2.move(dx, dy);
        point3.move(dx, dy);
    }

    public double area() {
        return Math.abs(0.5 * ((point1.getX() - point3.getX()) *
                (point2.getY() - point3.getY()) - (point2.getX() - point3.getX()) *
                (point1.getY() - point3.getY())));
    }

    public boolean pointContained(double x, double y) {
        double a = (point1.getX() - x) * (point2.getY() - point1.getY()) -
                (point2.getX() - point1.getX()) * (point1.getY() - y);
        double b = (point2.getX() - x) * (point3.getY() - point2.getY()) -
                (point3.getX() - point2.getX()) * (point2.getY() - y);
        double c = (point3.getX() - x) * (point1.getY() - point3.getY()) -
                (point1.getX() - point3.getX()) * (point3.getY() - y);
        if ((a > 0 && b > 0 && c > 0) || (a < 0 && b < 0 && c < 0)) return true;
        else return false;
    }

    public boolean pointContained(Point2D p) {
        return pointContained(p.getX(), p.getY());
    }

    private double distanceBetweenPoints(Point2D p1, Point2D p2) {
        return Math.sqrt((p2.getX() - p1.getX()) * (p2.getX() - p1.getX()) +
                (p2.getY() - p1.getY()) * (p2.getY() - p1.getY()));
    }

    public boolean isEquilateral() {
        return (0.000001 > Math.abs(distanceBetweenPoints(point1, point2) - distanceBetweenPoints(point1, point3)));
    }

    public void paint(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Point2D getPoint1() {
        return point1;
    }

    public void setPoint1(Point2D point1) {
        this.point1 = point1;
    }

    public Point2D getPoint2() {
        return point2;
    }

    public void setPoint2(Point2D point2) {
        this.point2 = point2;
    }

    public Point2D getPoint3() {
        return point3;
    }

    public void setPoint3(Point2D point3) {
        this.point3 = point3;
    }

    public String print() {
        return point1.print() + " - " + point2.print() + " - " + point3.print();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        if (point1 != null ? !point1.equals(triangle.point1) : triangle.point1 != null) return false;
        if (point2 != null ? !point2.equals(triangle.point2) : triangle.point2 != null) return false;
        if (point3 != null ? !point3.equals(triangle.point3) : triangle.point3 != null) return false;
        return color == triangle.color;
    }

    @Override
    public int hashCode() {
        int result = point1 != null ? point1.hashCode() : 0;
        result = 31 * result + (point2 != null ? point2.hashCode() : 0);
        result = 31 * result + (point3 != null ? point3.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
