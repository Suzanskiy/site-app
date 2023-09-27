package com.gravit.gravitlauncher.Controller;

import com.gravit.gravitlauncher.Authentication.AuthenticationRequest;
import com.gravit.gravitlauncher.Authentication.AuthenticationResponse;
import com.gravit.gravitlauncher.Authentication.RegisterRequest;
import com.gravit.gravitlauncher.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/registration")
    public ResponseEntity<String> register(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam String password) {
        RegisterRequest request = new RegisterRequest(userName, email, password);
        service.register(request);
        return ResponseEntity.ok(userName + " Registration successful");
    }
    @GetMapping("/isUserNameAvailable")
    public ResponseEntity<Boolean> isUserNameAvailable(@RequestParam String userName){
        boolean isAvalible = service.isUserNameAvailable(userName);
        return new ResponseEntity<>(isAvalible, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestParam String userName,
            @RequestParam String password) {
        AuthenticationRequest request = new AuthenticationRequest(userName, password);
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
