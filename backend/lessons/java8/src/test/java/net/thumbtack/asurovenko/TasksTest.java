package net.thumbtack.asurovenko;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

public class TasksTest {
    private static final Tasks TASKS = new Tasks();

    @Test
    public void testTask1NormalData() {
        String s = "Abc def yy";
        List<String> list = TASKS.split(s);
        List<String> list2 = new ArrayList<>();
        list2.add("Abc");
        list2.add("def");
        list2.add("yy");
        assertEquals(list, list2);
        int a = TASKS.count(list2);
        assertEquals(3, a);
    }

    @Test
    public void testTask1OneWordData() {
        String s = "Abcdefyy";
        List<String> list = TASKS.split(s);
        List<String> list2 = new ArrayList<String>();
        list2.add("Abcdefyy");
        assertEquals(list, list2);
        int a = TASKS.count(list);
        assertEquals(1, a);
    }

    @Test
    public void testTask1EmptyData() {
        String s = "";
        List<String> list = TASKS.split(s);
        List<String> list2 = new ArrayList<String>();
        list2.add("");
        assertEquals(list, list2);
        int a = TASKS.count(list);
        assertEquals(1, a);
    }

    @Test(expected = NullPointerException.class)
    public void testTask1WrongData() {
        String s = null;
        List<String> list = TASKS.split(s);
    }

    @Test(expected = NullPointerException.class)
    public void testTask1WrongData2() {
        List<String> list = null;
        TASKS.count(list);
    }

    @Test
    public void testTask4NormalData() {
        String s = "gf r";
        int int1 = TASKS.splitAndCountA(s);
        assertEquals(2, int1);
        int int2 = TASKS.splitAndCountB(s);
        assertEquals(2, int2);
    }

    @Test
    public void testTask4EmptyData() {
        String s = "";
        int int1 = TASKS.splitAndCountA(s);
        assertEquals(1, int1);
        int int2 = TASKS.splitAndCountB(s);
        assertEquals(1, int2);
    }

    @Test(expected = NullPointerException.class)
    public void testTask4WrongData() {
        String s = null;
        TASKS.splitAndCountA(s);
    }

    @Test
    public void testTask6NormalData() {
        double d1 = 14;
        double d2 = 24;
        double d = TASKS.max(d1, d2);
        assertEquals(d2, d);
    }

    @Test
    public void testTask6BigData() {
        double d1 = Double.MAX_VALUE;
        double d2 = Double.MAX_VALUE;
        double d = TASKS.max(d1, d2);
        assertEquals(d2, d);
        d1 = Double.MIN_VALUE;
        d2 = Double.MAX_VALUE;
        d = TASKS.max(d1, d2);
        assertEquals(d2, d);
    }

    @Test
    public void testTask7() {
        assertNotNull(TASKS.getCurrentDate());
    }

    @Test
    public void testTask9NormalData() {
        Integer i1 = 2345;
        Integer i2 = 2345;
        assertTrue(TASKS.areEqual(i1, i2));
        i2 = 43;
        assertFalse(TASKS.areEqual(i1, i2));
    }

    @Test
    public void testTask9MaxAndMinIntegerData() {
        Integer i1 = Integer.MAX_VALUE;
        Integer i2 = Integer.MAX_VALUE;
        assertTrue(TASKS.areEqual(i1, i2));
        i2 = Integer.MIN_VALUE;
        assertFalse(TASKS.areEqual(i1, i2));
    }

    @Test(expected = NullPointerException.class)
    public void testTask9NullData() {
        Integer i1 = Integer.MAX_VALUE;
        Integer i2 = null;
        assertFalse(TASKS.areEqual(i1, i2));
        i1 = null;
        TASKS.areEqual(i1, i2);
    }
}
