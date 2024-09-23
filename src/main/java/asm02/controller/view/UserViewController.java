package asm02.controller.view;

import asm02.dto.request.update.UserRequest;
import asm02.security.AuthUser;
import asm02.service.CVService;
import asm02.service.FileService;
import asm02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserViewController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CVService cvService;

    @GetMapping("/profile")
    public String profile(
            Model model,
            Locale locale,
            @AuthenticationPrincipal AuthUser authUser

    ) {
        if(authUser==null)
            throw new AccessDeniedException("Please login!");
        model.addAttribute("user", userService.findUser(authUser.getId()).orElseThrow(() -> new EntityNotFoundException("No such user with id: " + authUser.getId())));
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @Valid @ModelAttribute("user") UserRequest payload,
            BindingResult bindingResult,
            Locale locale,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        if (isNotAllow(authUser, payload.getId()))
            throw new AccessDeniedException("Deny to update other users profile");
        if (bindingResult.hasErrors())
            return "redirect:/user/profile";
        userService.update(payload);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.update", null, locale));
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        return "redirect:/user/profile";
    }

    @PostMapping("/cv")
    public String uploadCv(
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isDefault", defaultValue = "true") Boolean isDefault,
            RedirectAttributes redirectAttributes,
            Locale locale,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        if (isNotAllow(authUser, userId))
            throw new AccessDeniedException("Deny to update other users cv");
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("message.error.empty-file", null, locale)
            );
            redirectAttributes.addFlashAttribute("type", "error");
            redirectAttributes.addFlashAttribute("translated", true);
            return "redirect:/user/profile";
        }
        if (!fileService.isCv(file)) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("message.error.not-cv-file", null, locale)
            );
            redirectAttributes.addFlashAttribute("type", "error");
            redirectAttributes.addFlashAttribute("translated", true);
            return "redirect:/user/profile";
        }
        // TODO: 9/22/2024 Rename if duplicated!
        // Currently, if new file name is same with old one, does not thing
        // which may cause confusing when selecting cv to apply for job later.
        cvService.uploadCv(file, userId, isDefault);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.cv-uploaded", null, locale));
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        return "redirect:/user/profile";
    }

    @PostMapping("/avatar")
    public String uploadAvatar(
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes,
            Locale locale,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        if (isNotAllow(authUser, userId))
            throw new AccessDeniedException("Deny to update other users avatar");
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.error.empty-file", null, locale));
            redirectAttributes.addFlashAttribute("type", "error");
            redirectAttributes.addFlashAttribute("translated", true);
            return "redirect:/user/profile";
        }
        if (!fileService.isImage(file)) {
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.error.not-image-file", null, locale));
            redirectAttributes.addFlashAttribute("type", "error");
            redirectAttributes.addFlashAttribute("translated", true);
            return "redirect:/user/profile";
        }
        userService.uploadAvatar(userId, file);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.avatar-uploaded", null, locale));
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        return "redirect:/user/profile";
    }

    /**
     * Checking if principal is allowed to update targetUserId (by means same user)
     */
    private boolean isNotAllow(AuthUser authUser, Long targetUserId) {
        if (authUser == null)
            return true;
        return !authUser.getId().equals(targetUserId);
    }
}
