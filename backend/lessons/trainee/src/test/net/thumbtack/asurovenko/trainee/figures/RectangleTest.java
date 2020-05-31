package net.thumbtack.asurovenko.trainee.figures;

import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RectangleTest {
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testRectangle() {
        Rectangle rectangle = new Rectangle(10.11, 12.13, 14.15, 16.17);
        assertEquals(10.11, rectangle.getFirst().getX(), 0.000001);
        assertEquals(12.13, rectangle.getFirst().getY(), 0.000001);
        assertEquals(14.15, rectangle.getSecond().getX(), 0.000001);
        assertEquals(16.17, rectangle.getSecond().getY(), 0.000001);

        rectangle = new Rectangle();
        assertEquals(0, rectangle.getFirst().getX(), 0.000001);
        assertEquals(0, rectangle.getFirst().getY(), 0.000001);
        assertEquals(1, rectangle.getSecond().getX(), 0.000001);
        assertEquals(1, rectangle.getSecond().getY(), 0.000001);
    }

    @Test
    public void testPrint() {
        Rectangle rectangle = new Rectangle(1, 2, 3, 4);
        assertEquals("1.0:2.0\n3.0:2.0\n3.0:4.0\n1.0:4.0", rectangle.print());
    }

    @Test
    public void testMove() {
        Rectangle rectangle = new Rectangle(10.11, 12.13, 14.15, 16.17);
        rectangle.move(1, 5);
        assertEquals(11.11, rectangle.getFirst().getX(), 0.000001);
        assertEquals(17.13, rectangle.getFirst().getY(), 0.000001);
        assertEquals(15.15, rectangle.getSecond().getX(), 0.000001);
        assertEquals(21.17, rectangle.getSecond().getY(), 0.000001);
    }

    @Test
    public void testReduce() {
        Rectangle rectangle = new Rectangle(5, 10, 30, 40);
        rectangle.reduce(2, 3);
        assertEquals(5, rectangle.getFirst().getX(), 0.000001);
        assertEquals(10, rectangle.getFirst().getY(), 0.000001);
        assertEquals(17.5, rectangle.getSecond().getX(), 0.000001);
        assertEquals(20, rectangle.getSecond().getY(), 0.000001);
    }

    @Test
    public void testArea() {
        Rectangle rectangle = new Rectangle(10, 10, 20, 100);
        assertEquals(900, rectangle.area(), 0.000001);
    }

    @Test
    public void testPointContained() {
        Rectangle rectangle = new Rectangle(10, 10, 20, 100);
        assertTrue(rectangle.pointContained(15, 45));
    }

    @Test
    public void testPointNotContained() {
        Rectangle rectangle = new Rectangle(10, 10, 20, 100);
        assertFalse(rectangle.pointContained(150, 450));
    }

    @Test
    public void testCross() {
        Rectangle rectangle = new Rectangle(10, 10, 20, 100);
        assertTrue(rectangle.cross(new Rectangle(5, 50, 50, 70)));
    }

    @Test
    public void testNotCross() {
        Rectangle rectangle = new Rectangle(10, 10, 20, 100);
        assertFalse(rectangle.cross(new Rectangle(30, 0, 50, 10)));
    }

    @Test
    public void testRectangleContained() {
        Rectangle rectangle = new Rectangle(10, 10, 100, 100);
        assertTrue(rectangle.rectangleContained(new Rectangle(20, 25, 50, 70)));
    }

    @Test
    public void testRectangleNotContained() {
        Rectangle rectangle = new Rectangle(10, 10, 100, 100);
        assertFalse(rectangle.rectangleContained(new Rectangle(30, 0, 50, 10)));
    }

    @Test
    public void testLarge() {
        Rectangle rectangle = new Rectangle(1, 1, 2, 3);
        rectangle.large(3);
        assertTrue(rectangle.equals(new Rectangle(1, 1, 4, 7)));
    }

    @Test
    public void testColor() throws ColorException {
        Rectangle rectangle = new Rectangle(1, 1, 20, 15, "BLACK");
        assertEquals("BLACK", rectangle.getColor().name());
    }

    @Test(expected = ColorException.class)
    public void testWrongColor() throws ColorException {
        new Rectangle(1, 1, 20, 15, "BLK");
    }

    @Test
    public void testWriteAndRead() throws IOException, ClassNotFoundException {
        Rectangle rectangle = new Rectangle(0.001, -8.201, 63.258, 101.1);
        File file = folder.newFile();
        rectangle.writeToFile(file.getAbsolutePath());
        Rectangle rectangle2 = Rectangle.readFromFile(file.getAbsolutePath());
        assertEquals(0.001, rectangle2.getFirst().getX(), 0.000001);
        assertEquals(-8.201, rectangle2.getFirst().getY(), 0.000001);
        assertEquals(63.258, rectangle2.getSecond().getX(), 0.000001);
        assertEquals(101.1, rectangle2.getSecond().getY(), 0.000001);
    }

    @Test
    public void testWriteAndReadArray() throws IOException, ClassNotFoundException {
        Rectangle[] rectangles = new Rectangle[5];
        for (int i = 0; i < 5; i++) {
            rectangles[i] = new Rectangle(i + 23.01, i * 0.158 - 6.225, i * 2 + 105.210, i + 1.0005);
        }
        File file = folder.newFile();
        Rectangle.writeArrayToFile(file.getAbsolutePath(), rectangles);
        Rectangle[] rectangles2 = Rectangle.readArrayFromFile(file.getAbsolutePath(), 5);
        for (int i = 0; i < 5; i++) {
            assertTrue(rectangles[i].equals(rectangles2[i]));
        }
    }
}