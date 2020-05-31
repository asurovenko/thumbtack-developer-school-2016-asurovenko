package net.thumbtack.asurovenko.trainee.cars;

import net.thumbtack.asurovenko.trainee.exceptions.ColorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {
    @Test
    public void testCar() {
        Car car = new Car("Nissan", 1800, 260);
        assertEquals("Nissan", car.getMark());
        assertEquals(1800, car.getMass());
        assertEquals(260, car.getSpeed());
    }

    @Test
    public void testPrint() {
        Car car = new Car("Nissan", 1800, 260);
        assertEquals("Mark: Nissan\nMass: 1800\nSpeed: 260", car.print());
    }

    @Test
    public void testColor() throws ColorException {
        Car car = new Car("Nissan", 1800, 260, "RED");
        assertEquals("RED", car.getColor().name());
    }

    @Test(expected = ColorException.class)
    public void testWrongColor() throws ColorException {
        new Car("LADA", 1000, 100, "WrongColor");
    }
}