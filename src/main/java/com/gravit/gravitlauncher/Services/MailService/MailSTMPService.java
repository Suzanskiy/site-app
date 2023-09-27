package com.gravit.gravitlauncher.Services.MailService;

import com.gravit.gravitlauncher.Excpetion.MailServiceException.MailServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.gravit.gravitlauncher.Excpetion.MailServiceException.MailErrorCode.*;

@Service
@RequiredArgsConstructor
public class MailSTMPService implements MailService{

    private final JavaMailSender emailSendler;
    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {
        validateEmailParameters(toAddress, subject, message);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        try {
            emailSendler.send(simpleMailMessage);
        } catch (MailException me) {
            me.getMessage();
        }
    }

    private void validateEmailParameters(String toAddress, String subject, String message) {
        if (toAddress == null || toAddress.isEmpty()) {
            throw new MailServiceException(ADRESS_IS_NULL_OR_EMPTY);
        } else if (subject == null || subject.isEmpty()) {
            throw new MailServiceException(SUBJECT_IS_NULL_OR_EMPTY);
        } else if (message == null || message.isEmpty()) {
            throw new MailServiceException(MESSAGE_IS_NULL_OR_EMPTY);
        }
    }
}
