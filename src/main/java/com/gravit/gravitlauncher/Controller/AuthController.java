package com.gravit.gravitlauncher.Controller;

import com.gravit.gravitlauncher.Authentication.AuthenticationRequest;
import com.gravit.gravitlauncher.Authentication.AuthenticationResponse;
import com.gravit.gravitlauncher.Authentication.RegisterRequest;
import com.gravit.gravitlauncher.Exception.CustomException;
import com.gravit.gravitlauncher.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;


    @PostMapping("/registration")
    public ResponseEntity<?> register(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam String password) {
        RegisterRequest request = new RegisterRequest(userName, email, password);
        try {
            service.register(request);
            return createResponse(userName, true, userName + " Registration successful", HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);        }
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
    private ResponseEntity<?> createResponse (String userName,
                                                                   boolean success,
                                                                   String message,
                                                                   HttpStatus status) {
       AuthenticationResponse response = AuthenticationResponse.builder()
                .userName(userName)
                .success(success)
                .message(message)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
