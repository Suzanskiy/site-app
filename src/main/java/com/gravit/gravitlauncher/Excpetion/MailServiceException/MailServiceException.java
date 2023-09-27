package com.gravit.gravitlauncher.Excpetion.MailServiceException;

import lombok.RequiredArgsConstructor;

public class MailServiceException extends RuntimeException{

    private final MailErrorCode mailErrorCode;

    public MailServiceException(MailErrorCode mailErrorCode) {
        super(mailErrorCode.getMessage());
        this.mailErrorCode = mailErrorCode;
    }

    public MailServiceException(MailErrorCode mailErrorCode, String param) {
        super(mailErrorCode.formatMessage(param));
        this.mailErrorCode = mailErrorCode;
    }

    public MailErrorCode getMailErrorCode() {
        return mailErrorCode;
    }
}
