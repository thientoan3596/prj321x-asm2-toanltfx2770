package asm02.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");  // Trust Gmail's server
        javaMailProperties.put("mail.smtp.timeout", "5000");  // Optional, timeout for SMTP connection
        javaMailProperties.put("mail.smtp.connectiontimeout", "5000");  // Optional, connection timeout
        javaMailProperties.put("mail.smtp.writetimeout", "5000");
        mailSender.setJavaMailProperties(javaMailProperties);
//        mailSender.setUsername("liangsandoan@gmail.com");
//        mailSender.setPassword("tunl igjj jbow ghih");
        return mailSender;
    }
}
