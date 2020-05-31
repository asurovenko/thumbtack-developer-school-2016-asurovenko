package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.figures.Point2D;

public abstract class Figure {
    public abstract void move(double dx, double dy);

    public abstract double area();

    public abstract boolean pointContained(Point2D point);

    public abstract boolean pointContained(double x, double y);
}
