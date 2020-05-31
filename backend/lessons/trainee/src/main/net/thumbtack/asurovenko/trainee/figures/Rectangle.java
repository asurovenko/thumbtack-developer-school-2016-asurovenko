package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.Color;
import net.thumbtack.asurovenko.trainee.Figure;
import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import net.thumbtack.asurovenko.trainee.interfaces.Colored;

import java.io.*;
import java.util.ArrayList;

public class Rectangle extends Figure implements Colored, Serializable {
    private Point2D first;
    private Point2D second;
    private Color color;

    public Rectangle() {
        first = new Point2D(0, 0);
        second = new Point2D(1, 1);
    }

    public Rectangle(double x, double y) {
        first = new Point2D(0, 0);
        second = new Point2D(x, y);
    }

    public Rectangle(Point2D p) {
        first = new Point2D(0, 0);
        second = p;
    }

    public Rectangle(double x1, double y1, double x2, double y2) {
        this(new Point2D(x1, y1), new Point2D(x2, y2));
    }

    public Rectangle(double x1, double y1, double x2, double y2, String color) throws ColorException {
        this(x1, y1, x2, y2);
        this.color = Color.chooseColor(color);
    }

    public Rectangle(Point2D p1, Point2D p2) {
        first = p1;
        second = p2;
    }

    public static void writeArrayToFile(String filename, Object[] objects) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (Object o : objects) {
                oos.writeObject(o);
            }
        }
    }

    public static Rectangle[] readArrayFromFile(String filename, int countObject) throws IOException, ClassNotFoundException {
        try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Rectangle> rectangles = new ArrayList<>();
            Rectangle rectangle;
            for (int i = 0; i < countObject; i++) {
                rectangles.add((Rectangle) oin.readObject());
            }
            return rectangles.toArray(new Rectangle[rectangles.size()]);
        }
    }

    public static Rectangle readFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))) {
            return (Rectangle) oin.readObject();
        }
    }

    public void writeToFile(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }

    public static Rectangle readFromFile(String filename) throws IOException, ClassNotFoundException {
        return readFromFile(new File(filename));
    }

    public void writeToFile(String filename) throws IOException {
        writeToFile(new File(filename));
    }

    public void move(double dx, double dy) {
        first.move(dx, dy);
        second.move(dx, dy);
    }

    public void reduce(double nx, double ny) {
        second.setX((second.getX() - first.getX()) / nx + first.getX());
        second.setY((second.getY() - first.getY()) / ny + first.getY());
    }

    public void reduce(double n) {
        reduce(n, n);
    }

    public double area() {
        return (second.getX() - first.getX()) * (second.getY() - first.getY());
    }

    public boolean pointContained(double x, double y) {
        return first.getX() < x && second.getX() > x && first.getY() < y && second.getY() > y;
    }

    public boolean pointContained(Point2D p) {
        return pointContained(p.getX(), p.getY());
    }

    public boolean cross(Rectangle rect) {
        return !(first.getX() > rect.getSecond().getX() ||
                second.getX() < rect.getFirst().getX() ||
                second.getY() < rect.getFirst().getY() ||
                first.getY() > rect.getSecond().getY());
    }

    public boolean rectangleContained(Rectangle rectangle) {
        return pointContained(rectangle.getFirst()) && pointContained(rectangle.getSecond());
    }

    public Rectangle large(double n) {
        Rectangle rectangle = new Rectangle(first, second);
        double firstX = rectangle.getFirst().getX();
        double firstY = rectangle.getFirst().getY();
        double secondX = rectangle.getSecond().getX();
        double secondY = rectangle.getSecond().getY();
        rectangle.getSecond().setX((secondX - firstX) * n + firstX);
        rectangle.getSecond().setY((secondY - firstY) * n + firstY);
        return rectangle;
    }

    public void paint(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Point2D getFirst() {
        return first;
    }

    public void setFirst(Point2D first) {
        this.first = first;
    }

    public Point2D getSecond() {
        return second;
    }

    public void setSecond(Point2D second) {
        this.second = second;
    }

    public String print() { //точки перечисляются против часовой стрелки начиная с левой-нижней точки
        return first.print() + '\n' + printRightBottomPoint() + '\n' + second.print() + '\n' + printLeftTopPoint();
    }

    protected String printLeftTopPoint() {
        return first.getX() + ":" + second.getY();
    }

    protected String printRightBottomPoint() {
        return second.getX() + ":" + first.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        if (first != null ? !first.equals(rectangle.first) : rectangle.first != null) return false;
        if (second != null ? !second.equals(rectangle.second) : rectangle.second != null) return false;
        return color == rectangle.color;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}