package com.gravit.gravitlauncher.Controller;

import com.gravit.gravitlauncher.Services.EmailVerificationService.EmailVerificationServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationServiceImpl emailVerificationService;

    @GetMapping("verify-email")
    public void verifyEmail (@RequestParam String email, HttpServletResponse response) throws IOException {
        emailVerificationService.verifiEmail(email, response);
    }
}
