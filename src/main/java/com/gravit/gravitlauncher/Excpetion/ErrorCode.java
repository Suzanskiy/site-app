package com.gravit.gravitlauncher.Excpetion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NULL_REQUEST("Request is null"),
    USER_NAME_IS_NULL_OR_EMPTY("Username is null or EMPTY"),
    EMAIL_IS_NULL_OR_EMPTY("Email is null or EMPTY"),
    EMAIL_IS_BAD("This %s email is bad!"),
    SMPT_FAILURE("Failed to send email"),
    USER_NAME_IS_ALREADY_EXIST("This - %s Username is already exist!"),
    USER_NAME_NOT_FOUND("Username %s not found!");


    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String formatMessage(String param) {
        return String.format(message, param);
    }
}
