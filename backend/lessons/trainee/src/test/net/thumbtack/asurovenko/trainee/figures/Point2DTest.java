package net.thumbtack.asurovenko.trainee.figures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Point2DTest {
    @Test
    public void testPoint2D() {
        Point2D point2D = new Point2D(20, 34);
        assertEquals(20, point2D.getX(), 0.00001);
        assertEquals(34, point2D.getY(), 0.00001);
        point2D = new Point2D();
        assertEquals(0, point2D.getX(), 0.00001);
        assertEquals(0, point2D.getY(), 0.00001);
    }

    @Test
    public void testPrint() {
        Point2D point2D = new Point2D(10.23, 10.14);
        assertEquals("10.23:10.14", point2D.print());
    }

    @Test
    public void testMove() {
        Point2D point2D = new Point2D(3, 5);
        point2D.move(10, -20);
        assertEquals(13, point2D.getX(), 0.000001);
        assertEquals(-15, point2D.getY(), 0.000001);
    }
}