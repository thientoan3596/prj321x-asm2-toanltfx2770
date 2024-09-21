package asm02.controller.view;

import asm02.dto.request.update.UserRequest;
import asm02.security.AuthUser;
import asm02.service.CVService;
import asm02.service.FileService;
import asm02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
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
    public String profile(Model model, Principal principal, Locale locale) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Long userID = ((AuthUser) token.getPrincipal()).getId();
        model.addAttribute("user", userService.findUser(userID).orElseThrow(() -> new EntityNotFoundException("No such user with id: " + userID)));
        return "user/profile";
    }
    @PostMapping("/profile")
    public String updateProfile(
        @Valid @ModelAttribute("user") UserRequest payload,
        BindingResult bindingResult,
        Locale locale,
        Model model,
        RedirectAttributes redirectAttributes,
        Principal principal
    ){
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        if(!((AuthUser)token.getPrincipal()).getId().equals(payload.getId())){
            System.out.println("Principal " + ((AuthUser)token.getPrincipal()).getId() + " payload " + payload.getId());
            System.out.println("Ouch");
            throw new AccessDeniedException("Deny to update other users profile");
        }
        if(bindingResult.hasErrors())
            return "redirect:/user/profile";
        userService.update(payload);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.update", null, locale));
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        System.out.println("ALL GOOD");
        return "redirect:/user/profile";
    }

    @PostMapping("/cv")
    public String uploadCv(
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isDefault",defaultValue = "true") Boolean isDefault,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("message.error.empty-file",null,locale)
            );
            redirectAttributes.addFlashAttribute("type","error");
            redirectAttributes.addFlashAttribute("translated",true);
            return "redirect:/user/profile";
        }
        if(!fileService.isCv(file)){
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("message.error.not-cv-file",null,locale)
            );
            redirectAttributes.addFlashAttribute("type","error");
            redirectAttributes.addFlashAttribute("translated",true);
            return "redirect:/user/profile";
        }
         cvService.uploadCv(file,userId,isDefault);
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
            Locale locale
    ) {
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
}
