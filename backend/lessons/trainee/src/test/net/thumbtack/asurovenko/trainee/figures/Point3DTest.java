package net.thumbtack.asurovenko.trainee.figures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Point3DTest {
    @Test
    public void testPoint3D() {
        Point3D point3D = new Point3D(1, 2, 3);
        assertEquals(1, point3D.getX(), 0.00001);
        assertEquals(2, point3D.getY(), 0.00001);
        assertEquals(3, point3D.getZ(), 0.00001);
        point3D = new Point3D();
        assertEquals(0, point3D.getX(), 0.00001);
        assertEquals(0, point3D.getY(), 0.00001);
        assertEquals(0, point3D.getZ(), 0.00001);
    }

    @Test
    public void testPrint() {
        Point3D point3D = new Point3D(1, 2, 3);
        assertEquals("1.0:2.0:3.0", point3D.print());
    }

    @Test
    public void testMove() {
        Point3D point3D = new Point3D(1, 2, 3);
        point3D.move(5, 3, 10);
        assertEquals(6, point3D.getX(), 0.00001);
        assertEquals(5, point3D.getY(), 0.00001);
        assertEquals(13, point3D.getZ(), 0.00001);
    }
}