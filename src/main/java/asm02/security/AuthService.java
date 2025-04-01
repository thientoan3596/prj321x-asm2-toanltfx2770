package asm02.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    public void refreshAuthentication(String email) ;
}
