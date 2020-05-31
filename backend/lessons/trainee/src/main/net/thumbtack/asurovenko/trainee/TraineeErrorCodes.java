package net.thumbtack.asurovenko.trainee;

public enum TraineeErrorCodes {
    EMPTY("Строка null или пустая."),
    BAD_VALUE("Величина вне диапазона.");

    private String error;

    TraineeErrorCodes(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
