package asm02.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {
    @GetMapping({"/", "/home","/index"})
    public String home() {
        return "public/index";
    }
    @GetMapping("/login")
    public String login() {
        return "public/login";
    }
    @GetMapping("/register")
    public String register() {
        return "public/register";
    }
}
