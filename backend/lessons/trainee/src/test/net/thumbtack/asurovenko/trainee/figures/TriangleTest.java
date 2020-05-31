package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TriangleTest {
    @Test
    public void testTriangle() {
        Triangle triangle = new Triangle(10, 11, 20, 30, 21, 13);
        assertEquals(10, triangle.getPoint1().getX(), 0.000001);
        assertEquals(11, triangle.getPoint1().getY(), 0.000001);
        assertEquals(20, triangle.getPoint2().getX(), 0.000001);
        assertEquals(30, triangle.getPoint2().getY(), 0.000001);
        assertEquals(21, triangle.getPoint3().getX(), 0.000001);
        assertEquals(13, triangle.getPoint3().getY(), 0.000001);
    }

    @Test
    public void testPrint() {
        Triangle triangle = new Triangle(10, 11, 20, 30, 21, 13);
        assertEquals("10.0:11.0 - 20.0:30.0 - 21.0:13.0", triangle.print());
    }

    @Test
    public void testMove() {
        Triangle triangle = new Triangle(10, 11, 20, 30, 21, 13);
        triangle.move(15, 5);
        assertEquals(25, triangle.getPoint1().getX(), 0.000001);
        assertEquals(16, triangle.getPoint1().getY(), 0.000001);
        assertEquals(35, triangle.getPoint2().getX(), 0.000001);
        assertEquals(35, triangle.getPoint2().getY(), 0.000001);
        assertEquals(36, triangle.getPoint3().getX(), 0.000001);
        assertEquals(18, triangle.getPoint3().getY(), 0.000001);
    }

    @Test
    public void testArea() {
        Triangle triangle = new Triangle(4, 7, 10, -1, -10, -3);
        assertEquals(86, triangle.area(), 0.000001);
    }

    @Test
    public void testPointContained() {
        Triangle triangle = new Triangle(1, 4, 10, -9, 12, 11);
        assertTrue(triangle.pointContained(5, 2));
    }

    @Test
    public void testPointNotContained() {
        Triangle triangle = new Triangle(3, 5, 10, -4, 12, 41);
        assertFalse(triangle.pointContained(11, 5));
    }

    @Test
    public void testIsEquilateral() {
        Triangle triangle = new Triangle(1, 2, 1.5, Math.sqrt(3) * 0.5 + 2, 2, 2);
        assertTrue(triangle.isEquilateral());
    }

    @Test
    public void testIsNotEquilateral() {
        Triangle triangle = new Triangle(4, 5, 6, -7, 8, -9);
        assertFalse(triangle.isEquilateral());
    }

    @Test
    public void testColor() throws ColorException {
        Triangle triangle = new Triangle(1, 1, 20, 15, 12, 13, "WHITE");
        assertEquals("WHITE", triangle.getColor().name());
    }

    @Test(expected = ColorException.class)
    public void testWrongColor() throws ColorException {
        new Triangle(1, 1, 20, 15, 12, 13, "WHE");
    }
}