package com.gravit.gravitlauncher.Services.EmailVerificationService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface EmailVerificationService {

    void verifiEmail(String email, HttpServletResponse httpServletResponse) throws IOException;
}
