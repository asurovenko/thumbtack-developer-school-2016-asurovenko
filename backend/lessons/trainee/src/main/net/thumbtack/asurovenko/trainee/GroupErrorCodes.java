package net.thumbtack.asurovenko.trainee;

public enum GroupErrorCodes {
    EMPTY("Строка null или пустая."),
    BAD_ARRAY("Массив null или пуст.");

    private String error;

    GroupErrorCodes(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}