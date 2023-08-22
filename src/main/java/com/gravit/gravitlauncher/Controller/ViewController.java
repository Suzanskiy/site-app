package com.gravit.gravitlauncher.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "newLogin.html";
    }
    @GetMapping("/registration")
    public String showRegistrationPage(){
        return "newRegistration.html";
    }
}
