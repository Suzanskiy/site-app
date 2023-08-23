package com.gravit.gravitlauncher.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String homePage() {
            return "redirect:/login";
        }

    @GetMapping("/login")
    public String showLoginPage() {
        return "homePage.html";
    }
    @GetMapping("/registration")
    public String showRegistrationPage(){
        return "homePage.html";
    }
}
