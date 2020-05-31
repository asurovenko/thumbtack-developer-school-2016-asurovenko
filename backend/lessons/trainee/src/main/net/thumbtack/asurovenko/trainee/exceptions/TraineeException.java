package net.thumbtack.asurovenko.trainee.exceptions;

import net.thumbtack.asurovenko.trainee.TraineeErrorCodes;

public class TraineeException extends Exception {
    public TraineeException() {
        super();
    }

    public TraineeException(TraineeErrorCodes errorCode) {
        super(errorCode.getError());
    }

    public TraineeException(TraineeErrorCodes errorCode, Throwable cause) {
        super(errorCode.getError(), cause);
    }

    public TraineeException(Throwable cause) {
        super(cause);
    }
}
