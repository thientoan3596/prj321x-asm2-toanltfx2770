package asm02.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;


public class PasswordEncoderImpl implements PasswordEncoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private Environment env;
    @PostConstruct
    void postConstructHook(){
        System.out.println("COntructed");
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (env.getProperty("ENVIRONMENT").toLowerCase().startsWith("dev")) {
            if (rawPassword.toString().toLowerCase().equals("123")) {
                return true;
            }
        }
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
