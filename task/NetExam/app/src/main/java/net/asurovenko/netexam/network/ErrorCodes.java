package net.asurovenko.netexam.network;


import net.asurovenko.netexam.R;

public enum ErrorCodes {
    WRONG_OR_EMPTY_LOGIN(R.string.wrong_or_empty_login),
    WRONG_OR_EMPTY_PASSWORD(R.string.wrong_or_empty_password),
    LOGIN_ALREADY_EXISTS(R.string.login_already_exists),
    LOGIN_DOESNOT_EXIST(R.string.login_doesnot_exist),
    PASSWORD_NOT_MATCH(R.string.password_not_match),
    WRONG_TOKEN_PASSED(R.string.wrong_token_passed),
    WRONG_OR_EMPTY_FIRST_NAME(R.string.wrong_or_empty_first_name),
    WRONG_OR_EMPTY_LAST_NAME(R.string.wrong_or_empty_last_name),
    WRONG_PATRONYMIC(R.string.wrong_patronymic),
    WRONG_SEMESTER(R.string.wrong_semester),
    EMPTY_GROUP(R.string.empty_group),
    EMPTY_POSITION(R.string.empty_position),
    EMPTY_DEPARTMENT(R.string.empty_department),
    EMPTY_EXAM_NAME(R.string.empty_exam_name),
    EXAM_ALREADY_EXISTS(R.string.exam_already_exists);


    private int messageId;

    ErrorCodes(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }
}
