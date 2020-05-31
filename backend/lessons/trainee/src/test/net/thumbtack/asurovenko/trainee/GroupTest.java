package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.exceptions.GroupException;
import net.thumbtack.asurovenko.trainee.exceptions.TraineeException;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroupTest {
    @Test
    public void testGroup() throws GroupException, TraineeException {
        Trainee[] trainees = new Trainee[2];
        trainees[0] = new Trainee("alexey", "sorovenko");
        trainees[1] = new Trainee("sergey", "soloviev");
        Group group = new Group("SPB-401", trainees);
        assertTrue("SPB-401".equals(group.getName()));
        assertEquals(new Trainee("alexey", "sorovenko"), group.getTrainees()[0]);
        assertEquals(new Trainee("sergey", "soloviev"), group.getTrainees()[1]);
    }

    @Test(expected = GroupException.class)
    public void testGroupExceptionString1() throws GroupException, TraineeException {
        new Group("", new Trainee[]{new Trainee(), new Trainee()});
    }

    @Test(expected = GroupException.class)
    public void testGroupExceptionString2() throws GroupException, TraineeException {
        new Group(null, new Trainee[]{new Trainee(), new Trainee()});
    }

    @Test(expected = GroupException.class)
    public void testGroupExceptionArray1() throws GroupException, TraineeException {
        new Group("spb14", new Trainee[]{});
    }

    @Test(expected = GroupException.class)
    public void testGroupExceptionArray2() throws GroupException, TraineeException {
        new Group("spb14", null);
    }

    @Test
    public void testSortByTraineesValue() throws TraineeException, GroupException {
        Group group = new Group("group123", new Trainee[]{
                new Trainee("five", "f", 5),
                new Trainee("two", "t", 2),
                new Trainee("four", "fo", 4),
                new Trainee("three", "th", 3)});
        group.sortByTraineesValue();
        assertEquals(new Trainee("two", "t", 2), group.getTrainees()[0]);
        assertEquals(new Trainee("three", "th", 3), group.getTrainees()[1]);
        assertEquals(new Trainee("four", "fo", 4), group.getTrainees()[2]);
        assertEquals(new Trainee("five", "f", 5), group.getTrainees()[3]);
    }

    @Test
    public void testSortByTraineesName() throws TraineeException, GroupException {
        Group group = new Group("group123", new Trainee[]{
                new Trainee("five", "f", 5),
                new Trainee("two", "t", 2),
                new Trainee("four", "fo", 4),
                new Trainee("three", "th", 3)});
        group.sortByTraineesName();
        assertEquals(new Trainee("five", "f", 5), group.getTrainees()[0]);
        assertEquals(new Trainee("four", "fo", 4), group.getTrainees()[1]);
        assertEquals(new Trainee("three", "th", 3), group.getTrainees()[2]);
        assertEquals(new Trainee("two", "t", 2), group.getTrainees()[3]);
    }

    @Test
    public void testFindTraineeByName1() throws TraineeException, GroupException {
        Group group = new Group("group123", new Trainee[]{
                new Trainee("five", "f", 5),
                new Trainee("two", "t", 2),
                new Trainee("four", "fo", 4),
                new Trainee("three", "th", 3)});
        Trainee trainee = group.findTraineeByName("four");
        assertEquals(new Trainee("four", "fo", 4), trainee);
    }

    @Test
    public void testFindTraineeByName2() throws TraineeException, GroupException {
        Group group = new Group("group123", new Trainee[]{
                new Trainee("five", "f", 5),
                new Trainee("two", "t", 2),
                new Trainee("four", "fo", 4),
                new Trainee("three", "th", 3)});
        assertNull(group.findTraineeByName("name"));
    }
}