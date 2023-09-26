package com.gravit.gravitlauncher.Services.MailService;

public interface MailService {
    void sendSimpleEmail (String toAddress, String subject, String message);
}
