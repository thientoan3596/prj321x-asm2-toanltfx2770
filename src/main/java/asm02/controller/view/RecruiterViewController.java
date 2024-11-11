package asm02.controller.view;

import asm02.dto.request.insert.JobPostInsertRequest;
import asm02.dto.request.update.CompanyRequest;
import asm02.dto.request.update.JobPostRequest;
import asm02.dto.response.*;
import asm02.entity.eJobType;
import asm02.mapper.CompanyMapper;
import asm02.mapper.JobPostMapper;
import asm02.security.AuthUser;
import asm02.service.*;
import asm02.util.Sanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/recruiter")
@Validated
public class RecruiterViewController {
    @Value("${PAGINATION.JOB-POST.SIZE:10}")
    private int pageSize;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private JobPostMapper jobPostMapper;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;

    @Autowired
    private CVService cvService;
    @Autowired
    private JobPostService jobPostService;
    @Autowired
    private JobCategoryService jobCategoryService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ApplicationService applicationService;
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
        } else
            companyReq = companyMapper.toRequest(companyRes);
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
        payload.setDescription(Sanitizer.sanitizeContent(payload.getDescription()));
        if (isNotAllowed(authUser, payload.getId()))
            throw new AccessDeniedException("Deny to update other users company");
        CompanyResponse companyRes = companyService.findCompany(payload.getId()).orElse(null);
        model.addAttribute("company", companyRes);
        if (bindingResult.hasErrors()) {
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

    @GetMapping("/post-new-job")
    public String newJobPost(
            Model model,
            @AuthenticationPrincipal AuthUser authUser,
            Locale locale
    ) {
        UserResponse recruiter = userService.findUser(authUser.getId()).orElse(null);
        if (recruiter == null)
            throw new AccessDeniedException("Not A Recruiter");
        if (recruiter.getCompany() == null)
            throw new RuntimeException("Recruiter with no company!");
        JobPostInsertRequest jobPostInsertRequest = new JobPostInsertRequest();
        jobPostInsertRequest.setCompanyId(recruiter.getCompany().getId());
        model.addAttribute("jobPostModel", jobPostInsertRequest);
        model.addAttribute("types", eJobType.values());
        model.addAttribute("categories", jobCategoryService.findAll());
        return "recruiter/post-new-job";
    }

    @PostMapping("/post-new-job")
    public String insertJobPost(
            @Valid @ModelAttribute("jobPostModel") JobPostInsertRequest payload,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal AuthUser authUser,
            Locale locale,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", eJobType.values());
            model.addAttribute("categories", jobCategoryService.findAll());
            return "recruiter/post-new-job";
        }
        redirectAttributes.addFlashAttribute("message", "Thành công");
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        JobPostResponse response = jobPostService.insert(payload);

        // TODO: 10/17/2024 Redirect to list?
        return "redirect:/recruiter/jobs-posted";
    }

    @GetMapping("/jobs-posted")
    public String jobsPosted(
            @RequestParam(value = "page",defaultValue = "0") int page,
            Model model,
            @AuthenticationPrincipal AuthUser authUser,
            Locale locale
    ) {
        // TODO: 10/15/2024 Pagination
        CompanyResponse company = companyService.findCompanyByRecruiter(authUser.getId()).orElse(null);
        if (company == null) {
            System.out.println("ERROR!");
            return "NULL";
        }
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<JobPostResponse> jobs = jobPostService.findJobsPosted(company.getId(),pageable);
        model.addAttribute("posts", jobs.getContent());
        model.addAttribute("currentPage",jobs.getNumber());
        model.addAttribute("totalPage",jobs.getTotalPages());
        return "recruiter/jobs-posted";
    }

    @GetMapping("/job-post/{id}")
    public String viewJobPost(
            @PathVariable("id") Long postId,
            Model model,
            @AuthenticationPrincipal AuthUser authUser,
            Locale locale
    ) {
        // TODO: 10/19/2024 access denied ?
        JobPostResponse post = jobPostService.find(postId).orElseThrow(() -> new EntityNotFoundException("Job post not found"));
        if(!authUser.getCompanyId().equals(post.getCompanyId()))
            throw new AccessDeniedException("Access denied");
        JobPostRequest postUpdateRequest = jobPostMapper.toRequest(post);
        model.addAttribute("jobPostUpdateModel", postUpdateRequest);
        model.addAttribute("types", eJobType.values());
        model.addAttribute("categories", jobCategoryService.findAll());
        return "recruiter/job-post";
    }

    @PostMapping("/job-post/{id}")
    public String updateJobPost(
            @PathVariable("id") Long postId,
            Model model,
            @Valid @ModelAttribute("jobPostUpdateModel") JobPostRequest payload,
            BindingResult bindingResult,
            @AuthenticationPrincipal AuthUser authUser,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        // TODO: 10/29/2024 Access Denied?
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("jobPostUpdateModel", payload);
            model.addAttribute("types", eJobType.values());
            model.addAttribute("categories", jobCategoryService.findAll());
            return "/recruiter/job-post";
        }
        jobPostService.update(payload);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.update", null, locale));
        redirectAttributes.addFlashAttribute("type", "success");
        redirectAttributes.addFlashAttribute("translated", true);
        return "redirect:/recruiter/jobs-posted";
    }
    @GetMapping("application/{id}")
    public String viewCandidate(
            @PathVariable("id") Long applicationId,
            Model model,
            @AuthenticationPrincipal AuthUser authUser
    ){
        ApplicationResponse applicationDTO = applicationService.findApplication(applicationId).orElseThrow(() -> new EntityNotFoundException("Application not found"));
        JobPostResponse jobPostDTO =  jobPostService.find(applicationDTO.getJobId()).orElseThrow(()->new EntityNotFoundException("Job post not found"));
        if(authUser==null || !authUser.getCompanyId().equals(jobPostDTO.getCompanyId()))
            throw new AccessDeniedException("Access denied");
        UserResponse candidateDTO = userService.findUser(applicationDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
        CVResponse cvDTO = cvService.findById(applicationDTO.getCvId()).orElseThrow(()->new EntityNotFoundException("CV not found"));
        model.addAttribute("candidateResponse",candidateDTO);
        model.addAttribute("applicationResponse",applicationDTO);
        model.addAttribute("jobPostResponse",jobPostDTO);
        model.addAttribute("cvResponse",cvDTO);
        return "recruiter/candidate";
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
