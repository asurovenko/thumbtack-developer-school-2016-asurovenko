package net.thumbtack.asurovenko.trainee;

import com.google.gson.Gson;
import net.thumbtack.asurovenko.trainee.exceptions.TraineeException;

import java.io.Serializable;

public class Trainee implements Serializable, Comparable {
    private static final Gson gson = new Gson();
    private String name;
    private String surname;
    private int val;

    public Trainee(String name, String surname, int val) throws TraineeException {
        setName(name);
        setSurname(surname);
        setVal(val);
    }

    public Trainee(String name, String surname) throws TraineeException {
        this(name, surname, 1);
    }

    public Trainee() throws TraineeException {
        this("name", "surname", 1);
    }

    //  4.18
    public String toJson() {
        return gson.toJson(this);
    }

    //  4.18
    public static Trainee fromJson(String json) {
        return gson.fromJson(json, Trainee.class);
    }

    private boolean isTrueString(String field) {
        return field != null && field.trim().length() != 0;
    }

    private boolean isTrueInt(int field) {
        return field >= 1 && field <= 5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TraineeException {
        if (isTrueString(name)) {
            this.name = name.trim();
        } else {
            throw new TraineeException(TraineeErrorCodes.EMPTY);
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws TraineeException {
        if (isTrueString(surname)) {
            this.surname = surname.trim();
        } else {
            throw new TraineeException(TraineeErrorCodes.EMPTY);
        }
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) throws TraineeException {
        if (isTrueInt(val)) {
            this.val = val;
        } else {
            throw new TraineeException(TraineeErrorCodes.BAD_VALUE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        if (val != trainee.val) return false;
        if (name != null ? !name.equals(trainee.name) : trainee.name != null) return false;
        return surname != null ? surname.equals(trainee.surname) : trainee.surname == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + val;
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Trainee) o).getName());
    }
}