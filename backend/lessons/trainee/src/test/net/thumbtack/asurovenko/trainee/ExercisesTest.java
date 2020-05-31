package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.exceptions.TraineeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExercisesTest {
    private static Exercises exercises = new Exercises();

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testWriteAndReadTrainee() throws Exception {
        File file = folder.newFile();
        exercises.writeTrainee(file.getAbsolutePath(), new Trainee("alexey", "surovenko", 5));
        Trainee trainee = exercises.readTrainee(file.getAbsolutePath());
        assertEquals(new Trainee("alexey", "surovenko", 5), trainee);
    }

    @Test
    public void testWriteAndReadTraineeOneLine() throws IOException, TraineeException {
        File file = folder.newFile();
        exercises.writeTraineeOneLine(file.getAbsolutePath(), new Trainee("alexey", "surovenko", 5));
        Trainee trainee = exercises.readTraineeOneLine(file.getAbsolutePath());
        assertEquals(new Trainee("alexey", "surovenko", 5), trainee);
    }

    @Test
    public void testSerializeTrainee() throws IOException, TraineeException {
        File file = folder.newFile();
        exercises.serializeTrainee(file.getAbsolutePath(), new Trainee("alexey", "surovenko", 5));
        Trainee trainee = exercises.deserializeTrainee(file.getAbsolutePath());
        assertEquals(new Trainee("alexey", "surovenko", 5), trainee);
    }

    @Test
    public void testSerializeTraineeByteArray() throws TraineeException, IOException {
        byte[] bytes = exercises.serializeTraineeByteArray(new Trainee("alexey", "surovenko", 5));
        Trainee trainee = exercises.deserializeTraineeByteArray(bytes);
        assertEquals(new Trainee("alexey", "surovenko", 5), trainee);
    }

    @Test
    public void testWriteAndReadJsonFile() throws IOException, TraineeException {
        File file = folder.newFile();
        exercises.writeTraineeJsonToFile(file.getAbsolutePath(), new Trainee("alexey", "surovenko", 5));
        Trainee trainee = exercises.readTraineeJsonFromFile(file.getAbsolutePath());
        assertEquals(new Trainee("alexey", "surovenko", 5), trainee);
    }

    @Test
    public void testReadBytesFromFile() throws IOException, TraineeException {
        File file = folder.newFile();
        exercises.writeTraineeOneLine(file.getAbsolutePath(), new Trainee("Alexey", "Surovenko", 4));
        byte[] b = exercises.readBytesFromFile(file.getAbsolutePath());
        byte[] b2 = exercises.readBytesFromFileMappedBuffer(file.getAbsolutePath());
        assertTrue(Arrays.equals(b, b2));
    }

    @Test
    public void testRenameDatToBin() throws IOException {
        File newFolder = folder.newFolder("123");
        folder.newFile("123/hertkdfisjdfgoi234234.dat");
        folder.newFile("123/ljkdsfgklgk45654.dat");
        folder.newFile("123/ioertjndfgus34534.dat");
        exercises.renameDatToBin(newFolder.getAbsolutePath());
        assertTrue(new File(newFolder.getAbsolutePath() + "/hertkdfisjdfgoi234234.bin").exists());
        assertTrue(new File(newFolder.getAbsolutePath() + "/ljkdsfgklgk45654.bin").exists());
        assertTrue(new File(newFolder.getAbsolutePath() + "/ioertjndfgus34534.bin").exists());
    }
}