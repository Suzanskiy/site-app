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

//    @PostMapping("/registration")
//    public ResponseEntity<String> register(
//            @RequestParam String userName,
//            @RequestParam String email,
//            @RequestParam String password) {
//        RegisterRequest request = new RegisterRequest(userName, email, password);
//        service.register(request);
//        return ResponseEntity.ok(userName + " Registration successful");
//    }

    @PostMapping("/registration")
    public ResponseEntity<Map<String, Object>> register(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam String password) {
        RegisterRequest request = new RegisterRequest(userName, email, password);
        try {
            service.register(request);
            Map<String, Object> response = Map.of(
                    "success", true,
                    "message", userName + " Registration successful"
            );
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            Map<String, Object> response = Map.of(
                    "success", false,
                    "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            Map<String, Object> response = Map.of(
                    "success", false,
                    "message", "Internal Server Error"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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
