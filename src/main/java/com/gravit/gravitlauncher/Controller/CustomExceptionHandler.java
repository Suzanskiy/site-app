package com.gravit.gravitlauncher.Controller;

import com.gravit.gravitlauncher.Exception.CustomException;
import com.gravit.gravitlauncher.Exception.MailServiceException.MailServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleCustomException(CustomException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(MailServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleMailServiceException(MailServiceException mailEx) {
        return mailEx.getMessage();
    }
}
