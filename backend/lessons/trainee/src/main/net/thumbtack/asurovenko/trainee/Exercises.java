package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.exceptions.TraineeException;
import net.thumbtack.asurovenko.trainee.figures.Rectangle;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Exercises {
    //  4.11
    public void rectanglePrintStream(String filename) {
        Rectangle rectangle = new Rectangle();
        try (PrintStream ps = new PrintStream(new File(filename))) {
            ps.print(rectangle.print());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //  4.12
    public void writeTrainee(String filename, Trainee trainee) throws TraineeException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
            bw.write(trainee.getName());
            bw.newLine();
            bw.write(trainee.getSurname());
            bw.newLine();
            bw.write(Integer.toString(trainee.getVal()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //  4.13
    public Trainee readTrainee(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            return new Trainee(br.readLine(), br.readLine(), Integer.parseInt(br.readLine()));
        } catch (IOException | TraineeException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private StringBuilder SB;

    //  4.14
    public void writeTraineeOneLine(String filename, Trainee trainee) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
            SB = new StringBuilder(trainee.getName());
            SB.append(" ");
            SB.append(trainee.getSurname());
            SB.append(" ");
            SB.append(trainee.getVal());
            bw.write(SB.toString());
        }
    }

    //  4.15
    public Trainee readTraineeOneLine(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String[] s = br.readLine().split(" ");
            return new Trainee(s[0], s[1], Integer.parseInt(s[2]));
        } catch (IOException | TraineeException | NumberFormatException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //  4.16 - 1
    public void serializeTrainee(String filename, Trainee trainee) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(trainee);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //  4.16 - 2
    public Trainee deserializeTrainee(String filename) {
        try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(filename))) {
            return (Trainee) oin.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //  4.17 - 1
    public byte[] serializeTraineeByteArray(Trainee trainee) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(trainee.getName().length());
            baos.write(trainee.getName().getBytes("UTF-8"));
            baos.write(trainee.getSurname().length());
            baos.write(trainee.getSurname().getBytes("UTF-8"));
            baos.write(trainee.getVal());
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  4.17 - 2
    public Trainee deserializeTraineeByteArray(byte[] bytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            byte[] b = new byte[bais.read()];
            bais.read(b);
            String name = new String(b, "UTF-8");
            b = new byte[bais.read()];
            bais.read(b);
            String surname = new String(b, "UTF-8");
            return new Trainee(name, surname, bais.read());
        } catch (IOException | TraineeException e) {
            e.printStackTrace();
        }
        return null;
    }

    //4.19 - 1
    public void writeTraineeJsonToFile(String filename, Trainee trainee) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(new File(filename)))) {
            br.write(trainee.toJson());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //4.19 - 2
    public Trainee readTraineeJsonFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            return Trainee.fromJson(br.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //  5.1
    public byte[] readBytesFromFile(String filename) {
        try (FileChannel channel = new FileInputStream(new File(filename)).getChannel()) {
            int len = (int) channel.size();
            ByteBuffer buffer = ByteBuffer.allocate(len);
            channel.read(buffer);
            return buffer.array();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  5.2
    public byte[] readBytesFromFileMappedBuffer(String filename) {
        try (FileChannel channel = new RandomAccessFile(new File(filename), "rw").getChannel()) {
            int size = (int) channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, size);
            byte[] bytes = new byte[size];
            for (int i = 0; i < size; i++) {
                bytes[i] = buffer.get();
            }
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  5.3
    public void writeFileNumbers1_99(String filename) {
        try (FileChannel channel = new RandomAccessFile(new File(filename), "rw").getChannel()) {
            int size = 100 * 4;
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, size);
            for (int i = 0; i <= 99; i++) {
                buffer.putInt(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  5.6
    public void renameDatToBin(String path) {
        File[] files = new File(path).listFiles((dir, name) -> name.endsWith(".dat"));
        if (files != null) {
            for (File file : files) {
                file.renameTo(new File(file.getAbsolutePath().replaceAll(".dat", ".bin")));
            }
        }
    }
}
