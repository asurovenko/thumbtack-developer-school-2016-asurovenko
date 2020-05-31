package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Rectangle3DTest {
    @Test
    public void testRectangle3D() {
        Rectangle3D rectangle3D = new Rectangle3D(1, 2, 3, 4, 5);
        assertEquals(1, rectangle3D.getFirst().getX(), 0.000001);
        assertEquals(2, rectangle3D.getFirst().getY(), 0.000001);
        assertEquals(3, rectangle3D.getSecond().getX(), 0.000001);
        assertEquals(4, rectangle3D.getSecond().getY(), 0.000001);
        assertEquals(5, rectangle3D.getHeight(), 0.000001);
        rectangle3D = new Rectangle3D();
        assertEquals(0, rectangle3D.getFirst().getX(), 0.000001);
        assertEquals(0, rectangle3D.getFirst().getY(), 0.000001);
        assertEquals(1, rectangle3D.getSecond().getX(), 0.000001);
        assertEquals(1, rectangle3D.getSecond().getY(), 0.000001);
        assertEquals(1, rectangle3D.getHeight(), 0.000001);
    }

    @Test
    public void testPrint() {
        Rectangle3D rectangle3D = new Rectangle3D(1, 2, 3, 4, 5);
        assertEquals("1.0:2.0:0.0\n3.0:2.0:0.0\n3.0:4.0:0.0\n1.0:4.0:0.0\n1.0:2.0:5.0\n3.0:2.0:5.0\n3.0:4.0:5.0\n1.0:4.0:5.0",
                rectangle3D.print());
    }

    @Test
    public void testShrink() {
        Rectangle3D rectangle3D = new Rectangle3D(1, 2, 3, 4, 5);
        rectangle3D.shrink(2);
        assertEquals(2.5, rectangle3D.getHeight(), 0.000001);
    }

    @Test
    public void testVolume() {
        Rectangle3D rectangle3D = new Rectangle3D(1, 2, 3, 4, 5);
        assertEquals(20, rectangle3D.volume(), 0.000001);
    }

    @Test
    public void testPointContained() {
        Rectangle3D rectangle3D = new Rectangle3D(1, 2, 3, 4, 5);
        assertTrue(rectangle3D.pointContained(2, 3, 4));
    }

    @Test
    public void testPointNotContained() {
        Rectangle3D rectangle3D = new Rectangle3D(1, 2, 3, 4, 5);
        assertFalse(rectangle3D.pointContained(2, 3, 6));
    }

    @Test
    public void testRectangleContained() {
        Rectangle3D rectangle3D = new Rectangle3D(1, 1, 10, 10, 5);
        assertTrue(rectangle3D.rectangleContained(new Rectangle3D(5, 5, 7, 8, 3)));
    }

    @Test
    public void testRectangleNotContained() {
        Rectangle3D rectangle3D = new Rectangle3D(1, 1, 10, 10, 5);
        assertFalse(rectangle3D.rectangleContained(new Rectangle3D(5, 5, 7, 8, 10)));
    }

    @Test
    public void testColor() throws ColorException {
        Rectangle3D rectangle3D = new Rectangle3D(1, 1, 20, 15, 100, "WHITE");
        assertEquals("WHITE", rectangle3D.getColor().name());
    }

    @Test(expected = ColorException.class)
    public void testWrongColor() throws ColorException {
        new Rectangle3D(1, 1, 20, 15, 100, "WIE");
    }
}