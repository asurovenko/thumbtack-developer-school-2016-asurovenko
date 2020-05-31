package net.asurovenko.netexam.network.models;


public enum UserType {
    STUDENT("student"),
    TEACHER("teacher");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
