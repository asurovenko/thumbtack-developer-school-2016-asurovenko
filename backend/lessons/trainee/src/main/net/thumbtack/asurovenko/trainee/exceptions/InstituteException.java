package net.thumbtack.asurovenko.trainee.exceptions;

import net.thumbtack.asurovenko.trainee.InstituteErrorCodes;

public class InstituteException extends Exception {
    public InstituteException() {
        super();
    }

    public InstituteException(InstituteErrorCodes errorCode) {
        super(errorCode.getError());
    }

    public InstituteException(InstituteErrorCodes errorCode, Throwable cause) {
        super(errorCode.getError(), cause);
    }

    public InstituteException(Throwable cause) {
        super(cause);
    }
}
