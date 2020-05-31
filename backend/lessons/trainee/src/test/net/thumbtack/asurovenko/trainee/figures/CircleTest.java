package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CircleTest {
    @Test
    public void testCircle() {
        Circle circle = new Circle(10, 20, 5);

        assertEquals(10, circle.getCenter().getX(), 0.000001);
        assertEquals(20, circle.getCenter().getY(), 0.000001);
        assertEquals(5, circle.getRadius(), 0.000001);
    }

    @Test
    public void testPrint() {
        Circle circle = new Circle(10, 20, 5);
        assertEquals("10.0:20.0\nRadius = 5.0", circle.print());
    }

    @Test
    public void testMove() {
        Circle circle = new Circle(10, 20, 5);
        circle.move(1, 2);
        assertEquals(11, circle.getCenter().getX(), 0.000001);
        assertEquals(22, circle.getCenter().getY(), 0.000001);
    }

    @Test
    public void testArea() {
        Circle circle = new Circle(10, 20, 5);
        assertEquals(78.5398163375, circle.area(), 0.000001);
    }

    @Test
    public void testLength() {
        Circle circle = new Circle(10, 20, 5);
        assertEquals(31.415926535, circle.length(), 0.000001);
    }

    @Test
    public void testPointContained() {
        Circle circle = new Circle(1, 2, 10);
        assertTrue(circle.pointContained(5, 5));
    }

    @Test
    public void testPointNotContained() {
        Circle circle = new Circle(2, 1, 10);
        assertTrue(!circle.pointContained(15, 10));
    }

    @Test
    public void testColor() throws ColorException {
        Circle circle = new Circle(5, 8, 20, "YELLOW");
        assertEquals("YELLOW", circle.getColor().name());
    }

    @Test(expected = ColorException.class)
    public void testWrongColor() throws ColorException {
        new Circle(5, 8, 20, "YELAF");
    }
}