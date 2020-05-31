package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CylinderTest {
    @Test
    public void testCylinder() {
        Cylinder cylinder = new Cylinder(-9, 1, 5, 10);
        assertEquals(-9, cylinder.getCenter().getX(), 0.000001);
        assertEquals(1, cylinder.getCenter().getY(), 0.000001);
        assertEquals(5, cylinder.getRadius(), 0.000001);
        assertEquals(10, cylinder.getHeight(), 0.000001);
    }

    @Test
    public void testPrint() {
        Cylinder cylinder = new Cylinder(-1, 1, 5, 10);
        assertEquals("-1.0:1.0\nRadius = 5.0\nHeight = 10.0", cylinder.print());
    }

    @Test
    public void testVolume() {
        Cylinder cylinder = new Cylinder(1, 2, 5, 10);
        assertEquals(785.398163397, cylinder.volume(), 0.000001);
    }

    @Test
    public void testAreaSide() {
        Cylinder cylinder = new Cylinder(2, 3, 5, 10);
        assertEquals(314.159265358, cylinder.areaSide(), 0.000001);
    }

    @Test
    public void testPointContained() {
        Cylinder cylinder = new Cylinder(1, 2, 5, 10);
        assertTrue(cylinder.pointContained(2, 3, 5));
    }

    @Test
    public void testPointNotContained() {
        Cylinder cylinder = new Cylinder(-5, 1, 5, 10);
        assertFalse(cylinder.pointContained(2, 3, 11));
    }

    @Test
    public void testColor() throws ColorException {
        Cylinder cylinder = new Cylinder(1, 5, 20, 1, "YELLOW");
        assertEquals("YELLOW", cylinder.getColor().name());
    }

    @Test(expected = ColorException.class)
    public void testWrongColor() throws ColorException {
        new Cylinder(1, 5, 20, 1, "123");
    }
}