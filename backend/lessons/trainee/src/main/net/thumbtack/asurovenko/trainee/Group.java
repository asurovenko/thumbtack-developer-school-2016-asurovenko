package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.exceptions.GroupException;

import java.util.Arrays;

public class Group {
    private String name;
    private Trainee[] trainees;

    public Group(String name, Trainee[] trainees) throws GroupException {
        setName(name);
        setTrainees(trainees);
    }

    private boolean isTrueString(String field) {
        return field != null && field.trim().length() != 0;
    }

    private boolean isTrueArray(Trainee[] array) {
        return array != null && array.length != 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws GroupException {
        if (isTrueString(name)) {
            this.name = name.trim();
        } else {
            throw new GroupException(GroupErrorCodes.EMPTY);
        }
    }

    public Trainee[] getTrainees() {
        return trainees;
    }

    public void setTrainees(Trainee[] trainees) throws GroupException {
        if (isTrueArray(trainees)) {
            this.trainees = trainees;
        } else {
            throw new GroupException(GroupErrorCodes.BAD_ARRAY);
        }
    }

    public void sortByTraineesValue() {
        Arrays.sort(trainees, (p1, p2) -> Integer.compare(p1.getVal(), p2.getVal()));
    }

    public void sortByTraineesName() {
        Arrays.sort(trainees, (p1, p2) -> p1.getName().compareTo(p2.getName()));
    }

    public Trainee findTraineeByName(String name) {
        for (Trainee trainee : trainees) {
            if (trainee.getName().equals(name)) {
                return trainee;
            }
        }
        return null;
    }
}
