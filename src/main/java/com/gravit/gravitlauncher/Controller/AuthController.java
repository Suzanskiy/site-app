package com.gravit.gravitlauncher.Controller;

import com.gravit.gravitlauncher.Authentication.AuthenticationRequest;
import com.gravit.gravitlauncher.Authentication.AuthenticationResponse;
import com.gravit.gravitlauncher.Authentication.RegisterRequest;
import com.gravit.gravitlauncher.Entity.UserEntity;
import com.gravit.gravitlauncher.Services.AuthenticationService;
import com.gravit.gravitlauncher.Services.UserService.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;
    private final UserServiceImpl userService;

    @PostMapping("/registration")
    public ResponseEntity<String> register(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam String password) {
        RegisterRequest request = new RegisterRequest(userName, email, password);
        service.register(request);
        return ResponseEntity.ok(userName + " Registration successful");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestParam String userName,
            @RequestParam String password) {
        AuthenticationRequest request = new AuthenticationRequest(userName, password);
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail (@RequestParam String email) {

        boolean isVerified = userService.verifyEmail(email);

        if (isVerified) {
            return ResponseEntity.ok("good");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ivalid email");
        }
    }
}
