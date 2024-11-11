package asm02.advice;

import asm02.dto.response.UserResponse;
import asm02.security.AuthUser;
import asm02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalAdvice {
    @Autowired
    private UserService userService;
    @ModelAttribute("currentUser")
    public UserResponse currentUser(){
        // TODO: 10/29/2024 Partially updated AuthUser, confirm if this is still required? 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof AuthUser)
            return  userService.findUser(((AuthUser) authentication.getPrincipal()).getId()).orElse(null);
        return null;
    }
    /* Making current URL available to Thymeleaf Template */
    @ModelAttribute("aURL")
    public String getAbsoluteURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null)
            url += "?" + queryString;
        return url;
    }
}
