package net.thumbtack.asurovenko.trainee;

public enum InstituteErrorCodes  {
    EMPTY("Строка null или пустая.");

    private String error;

    InstituteErrorCodes(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
