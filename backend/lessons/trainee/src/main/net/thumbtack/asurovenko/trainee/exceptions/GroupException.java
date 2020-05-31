package net.thumbtack.asurovenko.trainee.exceptions;

import net.thumbtack.asurovenko.trainee.GroupErrorCodes;

public class GroupException extends Exception {
    public GroupException() {
        super();
    }

    public GroupException(GroupErrorCodes errorCode) {
        super(errorCode.getError());
    }

    public GroupException(GroupErrorCodes errorCode, Throwable cause) {
        super(errorCode.getError(), cause);
    }

    public GroupException(Throwable cause) {
        super(cause);
    }
}
