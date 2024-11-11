package asm02.service;

public interface MailService {
    String sendVerificationCode(String email);
    void sendEmail(String to, String subject, String body);
    boolean testConnection();
}
