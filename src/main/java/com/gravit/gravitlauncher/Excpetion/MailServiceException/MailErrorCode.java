package com.gravit.gravitlauncher.Excpetion.MailServiceException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MailErrorCode {

    ADRESS_IS_NULL_OR_EMPTY("Adress is null or empty"),
    SUBJECT_IS_NULL_OR_EMPTY("Subject is null or empty"),
    MESSAGE_IS_NULL_OR_EMPTY("Message is null or empty");

    private String message;
    MailErrorCode(String message) {
        this.message = message;
    }
    public String formatMessage(String param) {
        return String.format(message, param);
    }
}
