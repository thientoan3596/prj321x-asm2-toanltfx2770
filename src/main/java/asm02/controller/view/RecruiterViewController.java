package asm02.controller.view;

import asm02.dto.request.update.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import asm02.dto.response.UserResponse;
import asm02.mapper.CompanyMapper;
import asm02.security.AuthUser;
import asm02.service.CompanyService;
import asm02.service.FileService;
import asm02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/recruiter")
@Validated
public class RecruiterViewController {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MessageSource messageSource;
    @GetMapping("/company")
    public String viewCompany(
            Model model,
            @AuthenticationPrincipal AuthUser authUser,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        CompanyResponse companyRes = companyService.findCompanyByRecruiter(authUser.getId()).orElse(null);
        CompanyRequest companyReq = null;
        if (companyRes == null) {
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.error.recruiter-with-no-company", null, locale));
            redirectAttributes.addFlashAttribute("type", "error");
            redirectAttributes.addFlashAttribute("translated", true);
        }else
            companyReq =  companyMapper.toRequest(companyRes);
        model.addAttribute("company", companyRes);
        model.addAttribute("companyUpdateModel", companyReq);
        return "recruiter/company";
    }

    @PostMapping("/company")
    public String updateCompany(
            @Valid @ModelAttribute("companyUpdateModel") CompanyRequest payload,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal AuthUser authUser,
            Locale locale,
            RedirectAttributes redirectAttributes
    ) {
        if (isNotAllowed(authUser, payload.getId()))
            throw new AccessDeniedException("Deny to update other users company");
        CompanyResponse companyRes = companyService.findCompany(payload.getId()).orElse(null);
        model.addAttribute("company", companyRes);
        if (bindingResult.hasErrors()){
            return "/recruiter/company";
        }
        companyService.update(payload);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.update", null, locale));
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        return "redirect:/recruiter/company";
    }

    @PostMapping("/company-logo")
    public String updateCompanyLogo(
            @RequestParam("companyId") Long companyId,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes,
            Locale locale,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        if (isNotAllowed(authUser, companyId))
            throw new AccessDeniedException("Deny to update other users company");
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.error.empty-file", null, locale));
            redirectAttributes.addFlashAttribute("type", "error");
            redirectAttributes.addFlashAttribute("translated", true);
            return "redirect:/recruiter/company";
        }
        if (!fileService.isImage(file)) {
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.error.not-image-file", null, locale));
            redirectAttributes.addFlashAttribute("type", "error");
            redirectAttributes.addFlashAttribute("translated", true);
            return "redirect:/recruiter/company";
        }
        companyService.uploadLogo(companyId, file);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.logo-uploaded", null, locale));
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        return "redirect:/recruiter/company";
   }
    /**
     * Checking if principal is allowed to update targetCompanyID.
     */
    private boolean isNotAllowed(AuthUser authUser, Long targetCompanyId) {
        if (authUser == null)
            return true;
        UserResponse recruiter = userService.findUser(authUser.getId()).orElse(null);
        return recruiter == null || !recruiter.getCompany().getId().equals(targetCompanyId);
    }
}
