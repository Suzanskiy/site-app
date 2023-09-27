package com.gravit.gravitlauncher.Controller;

import com.gravit.gravitlauncher.Authentication.AuthenticationRequest;
import com.gravit.gravitlauncher.Authentication.AuthenticationResponse;
import com.gravit.gravitlauncher.Authentication.RegisterRequest;
import com.gravit.gravitlauncher.DB.DAO.UserRepository;
import com.gravit.gravitlauncher.Entity.UserEntity;
import com.gravit.gravitlauncher.Services.AuthenticationService;
import com.gravit.gravitlauncher.Services.UserService.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

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
    @GetMapping("/verify-email")
    public void verifyEmail(@RequestParam String email, HttpServletResponse response) throws IOException {
        Optional<UserEntity> userEntytyOptional = userRepository.findByEmailIgnoreCase(email);
        if(userEntytyOptional.isEmpty()) {
            response.sendRedirect("/api/login?error=Invalid%20email");
            return;
        }
        UserEntity userEntity = userEntytyOptional.get();
        if(userEntity.getIsEmailVerify()) {
            response.sendRedirect("/api/login?error=Email%20already%20verified");
            return;
        }
        userEntity.setIsEmailVerify(true);
        userRepository.save(userEntity);
        response.sendRedirect("/api/login?emailVerified=true");

    }

}
