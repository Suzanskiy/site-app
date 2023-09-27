package com.gravit.gravitlauncher.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

    @GetMapping({"/", "/api/login", "/api/registration"})
    public String showLoginPage() {
        return "homePage";
    }
    @GetMapping("/api/afterLoginPage")
            public String showAfterLoginPage() {
        return "afterLoginPage";
    }
}
