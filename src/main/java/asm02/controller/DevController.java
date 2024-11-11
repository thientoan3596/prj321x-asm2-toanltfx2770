package asm02.controller;

import asm02.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("dev")
public class DevController {
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @GetMapping("test")
    public String test() {
        return String.valueOf(emailServiceImpl.testConnection());
    }


    @GetMapping("clearSession")
    public String clearSession(HttpSession session){
        session.invalidate();
        return "OKE";
    }
    @RequestMapping("/sendEmail")
    public String sendEmail() {
        emailServiceImpl.sendEmail("toanlt3596@gmail.com", "Test Subject", "This is a test email.");
        return "emailSent"; // View name to confirm the email was sent
    }
}
