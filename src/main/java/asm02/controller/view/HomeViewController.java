package asm02.controller.view;

import asm02.dto.request.insert.UserRegisterRequest;
import asm02.dto.response.UserResponse;
import asm02.entity.eUserRole;
import asm02.security.AuthUser;
import asm02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class HomeViewController {
    private final RequestCache requestCache = new HttpSessionRequestCache();
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping({"/", "/home", "/index"})
    public String home(
            Model model,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        UserResponse currentUser =  authUser == null ? null : userService.findUser(authUser.getId()).orElse(null);
        model.addAttribute("user",currentUser);
        return "public/index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        SavedRequest savedRequest = requestCache.getRequest(request, null);
        if (savedRequest != null)
            model.addAttribute("redirectUrl", savedRequest.getRedirectUrl());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != "anonymousUser")
            return "redirect:/";
        return "public/login";
    }

    @GetMapping("/register")
    public String register(
            Model model,
            Locale locale
    ) {
        model.addAttribute("user", new UserRegisterRequest());
        model.addAttribute("roles", eUserRole.getLocalizedValues(messageSource, locale));
        return "public/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") UserRegisterRequest user,
            BindingResult bindingResult,
            Locale locale,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            String errorMessage = messageSource.getMessage("error.user.register.password.mismatch", null, locale);
            bindingResult.addError(
                    new FieldError("user", "passwordConfirm", errorMessage)
            );
            bindingResult.addError(
                    new FieldError("user", "password", errorMessage)
            );
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", eUserRole.getLocalizedValues(messageSource, locale));
            return "public/register";
        }

        userService.insert(user);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.register", null, locale));
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        return "redirect:/login";
    }

    @RequestMapping("/login-success")
    public String loginSuccess(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirectUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : "/";
        return "redirect:" + redirectUrl;
    }
}
