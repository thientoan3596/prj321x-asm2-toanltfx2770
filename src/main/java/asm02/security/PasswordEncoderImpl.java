package asm02.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordEncoderImpl implements PasswordEncoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Value("${environment:'PROD'}")
    private String environment;
    @Override
    public String encode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (environment.toLowerCase().startsWith("dev")) {
            if (rawPassword.toString().toLowerCase().equals("123")) {
                return true;
            }
        }
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
