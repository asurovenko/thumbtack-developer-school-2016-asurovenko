package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.exceptions.InstituteException;

public class Institute {
    private String name;
    private String city;

    public Institute(String name, String city) throws InstituteException {
        setName(name);
        setCity(city);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InstituteException {
        if (isEmpty(name)) {
            this.name = name.trim();
        } else {
            throw new InstituteException(InstituteErrorCodes.EMPTY);
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws InstituteException {
        if (isEmpty(city)) {
            this.city = city.trim();
        } else {
            throw new InstituteException(InstituteErrorCodes.EMPTY);
        }
    }

    private boolean isEmpty(String field) {
        return field != null && field.trim().length() != 0;
    }
}
