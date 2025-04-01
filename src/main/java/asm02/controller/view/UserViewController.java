package asm02.controller.view;

import asm02.dto.request.base.CompanyFollowRequest;
import asm02.dto.request.base.JobFollowRequest;
import asm02.dto.request.update.UserRequest;
import asm02.dto.response.ApplicationWithJobPostLogoResponse;
import asm02.dto.response.CompanyFollowResponse;
import asm02.dto.response.JobFollowResponse;
import asm02.security.AuthService;
import asm02.security.AuthUser;
import asm02.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserViewController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private CVService cvService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FollowService followService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AuthService authService;


    @GetMapping("/profile")
    public String profile(
            Model model,
            Locale locale,
            @AuthenticationPrincipal AuthUser authUser

    ) {
        if (authUser == null)
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
        authService.refreshAuthentication(authUser.getEmail());
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

    @GetMapping("applied-jobs")
    public String appliedJobs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<ApplicationWithJobPostLogoResponse> applications = applicationService.findByUserId_JobPostWithLogo(authUser.getId(), pageable);
        model.addAttribute("applications", applications.getContent());
        model.addAttribute("totalPage", applications.getTotalPages());
        model.addAttribute("currentPage", applications.getNumber());
        return "user/applied-jobs";
    }

    @GetMapping("saved-companies")
    public String savedCompanies(
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<CompanyFollowResponse> followResponse = followService.findFollowingCompanies(authUser.getId(), pageable);
        model.addAttribute("companies", followResponse.getContent());
        model.addAttribute("totalPage", followResponse.getTotalPages());
        model.addAttribute("currentPage", followResponse.getNumber());
        return "user/saved-companies";
    }
    @GetMapping("saved-jobs")
    public String savedJobs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal AuthUser authUser
    ){
        Pageable pageable = PageRequest.of(page,10);
        Page<JobFollowResponse> followResponse = followService.findFollowingJobs(authUser.getId(), pageable);
        model.addAttribute("jobs",followResponse.getContent());
        model.addAttribute("totalPage", followResponse.getTotalPages());
        model.addAttribute("currentPage", followResponse.getNumber());
        model.addAttribute("cvList", cvService.findByUserId(authUser.getId()));
        return "user/saved-jobs";
    }

    @PostMapping("follow-company")
    public String followCompany(
            @ModelAttribute CompanyFollowRequest followRequest,
            HttpServletRequest request,
            RedirectAttributes redirAttr,
            Locale locale
    ) {
        followService.insert(followRequest);
        String refer = request.getHeader("referer");
        redirAttr.addFlashAttribute("message", messageSource.getMessage("message.success.followed-company", null, locale));
        redirAttr.addFlashAttribute("type", "success");
        redirAttr.addFlashAttribute("translated", true);
        System.out.println(refer);
        return "redirect:" + refer;
    }

    @PostMapping("unfollow-company")
    public String unfollowCompany(
            @RequestParam Long followId,
            HttpServletRequest request,
            RedirectAttributes redirAttr,
            Locale locale
    ) {
        followService.unfollowCompany(followId);
        String refer = request.getHeader("referer");
        redirAttr.addFlashAttribute("message", messageSource.getMessage("message.success.unfollowed-company", null, locale));
        redirAttr.addFlashAttribute("type", "success");
        redirAttr.addFlashAttribute("translated", true);
        return "redirect:" + refer;
    }

    @PostMapping("follow-job")
    public String followJob(
            @ModelAttribute JobFollowRequest followRequest,
            HttpServletRequest request,
            RedirectAttributes redirAttr,
            Locale locale
    ) {
        followService.insert(followRequest);
        String refer = request.getHeader("referer");
        redirAttr.addFlashAttribute("message", messageSource.getMessage("message.success.followed-job", null, locale));
        redirAttr.addFlashAttribute("type", "success");
        redirAttr.addFlashAttribute("translated", true);
        System.out.println(refer);
        return "redirect:" + refer;
    }

    @PostMapping("unfollow-job")
    public String unfollowJob(
            @RequestParam Long followId,
            HttpServletRequest request,
            RedirectAttributes redirAttr,
            Locale locale
    ) {
        followService.unfollowJob(followId);
        String refer = request.getHeader("referer");
        redirAttr.addFlashAttribute("message", messageSource.getMessage("message.success.unfollowed-job", null, locale));
        redirAttr.addFlashAttribute("type", "success");
        redirAttr.addFlashAttribute("translated", true);
        return "redirect:" + refer;
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
