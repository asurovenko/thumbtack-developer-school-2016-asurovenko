package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.exceptions.TraineeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TraineeTest {
    @Test
    public void testTraineeEmpty() throws TraineeException {
        Trainee trainee = new Trainee();
        assertEquals("name", trainee.getName());
        assertEquals("surname", trainee.getSurname());
        assertEquals(1, trainee.getVal());
    }

    @Test
    public void testTrainee() throws TraineeException {
        Trainee trainee = new Trainee("Alexey", "Surovenko", 4);
        assertEquals("Alexey", trainee.getName());
        assertEquals("Surovenko", trainee.getSurname());
        assertEquals(4, trainee.getVal());
    }

    @Test(expected = TraineeException.class)
    public void testTraineeExceptionString1() throws TraineeException {
        new Trainee("AbcRe", new String(""), 4);
    }

    @Test(expected = TraineeException.class)
    public void testTraineeExceptionString2() throws TraineeException {
        new Trainee("   ", "Qweas", 4);
    }

    @Test(expected = TraineeException.class)
    public void testTraineeExceptionNullString() throws TraineeException {
        new Trainee(null, "Qweas", 4);
    }

    @Test(expected = TraineeException.class)
    public void testTraineeExceptionInt() throws TraineeException {
        new Trainee("AbcRe", "IthfYd", 6);
    }

    //  4.18
    @Test
    public void testJSON() throws TraineeException {
        Trainee trainee = new Trainee("name", "surname", 4);
        String jsonTraineeString = trainee.toJson();
        Trainee trainee2 = Trainee.fromJson(jsonTraineeString);
        assertEquals(trainee2, trainee);
        assertEquals("{\"name\":\"name\",\"surname\":\"surname\",\"val\":4}", jsonTraineeString);
    }
}