package com.gravit.gravitlauncher.Controller;

import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Services.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;


@PostMapping("/registration")
public ResponseEntity<String> registerUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password) {
    UserDTO userDTO = new UserDTO();
    userDTO.setUserName(userName);
    userDTO.setEmail(email);
    userDTO.setPassword(password);
    try{
        userService.registerUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

@PostMapping("/login")
    public ResponseEntity<String> loginUser (@RequestParam String userName, @RequestParam String password) {
        if (userService.loginUser(userName, password)) {
            return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad login or password", HttpStatus.BAD_REQUEST);
        }
}

}
