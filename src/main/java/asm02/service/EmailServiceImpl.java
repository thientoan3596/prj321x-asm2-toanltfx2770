package asm02.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Random;

@Service
public class EmailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${email.username:'example@email.com'}")
    private String sender;

    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;
    private static String pool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static Random rand = new Random();
    private static Integer codeLength = 6;
    @Override
    public boolean testConnection(){
        if (mailSender instanceof JavaMailSenderImpl) {
            JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) mailSender;
            try {
                mailSenderImpl.testConnection();
                System.out.println("Mail Connection Test Passed");
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Mail Connection Test Failed");
                System.out.println("Detail: "+username+"|"+password);
                return false;
            }
        }
        System.out.println("No proper method of testConnection");
        return false;
    }
    @Override
    public String sendVerificationCode(String email) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int index = rand.nextInt(pool.length()); // Random index in the pool
            code.append(pool.charAt(index)); // Append character from the pool
        }
//        sendEmail(email, "Verification Code", "Your verification code is: " + code.toString());
        System.out.println("Sent "+code);
        return code.toString();
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(sender);
        mailSender.send(message);
    }
}
