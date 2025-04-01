package asm02.controller.view;

import asm02.dto.request.insert.UserRegisterRequest;
import asm02.dto.response.*;
import asm02.entity.eUserRole;
import asm02.security.AuthUser;
import asm02.service.*;
import asm02.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;

@Controller
public class PublicViewController {
  private final RequestCache requestCache = new HttpSessionRequestCache();
  @Autowired
  private UserService userService;
  @Autowired
  private ApplicationService applicationService;
  @Autowired
  private JobPostService jobPostService;
  @Autowired
  private JobCategoryService jobCategoryService;
  @Autowired
  private CompanyService companyService;
  @Autowired
  private FollowService followService;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private MailService mailService;

  @Value("${pagination.jobs.size:12}")
  private int jobPageSize;

  @Value("${pagination.companies.size:10}")
  private int companyPageSize;

  @Value("${pagination.applications.size:12}")
  private int applicationPageSize;
  @Value("${file.upload-dir}")
  private String uploadDir;

  @Value("${file.cv-dir}")
  private String cvDir;
  @Value("${email.verification}")
  private Boolean mailVerification;
  @Autowired
  private ServletContext servletContext;

  @GetMapping({ "/", "/home", "/index" })
  public String home(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "comp", defaultValue = "") String companyLike,
      @RequestParam(value = "location", defaultValue = "") String locationLike,
      @RequestParam(value = "post", defaultValue = "") String postRelated,
      Model model,
      @AuthenticationPrincipal AuthUser authUser,
      HttpServletRequest servletReq) {
    model.addAttribute("fullPath", PaginationUtil.getFullURL(servletReq));
    UserResponse currentUser = authUser == null ? null : userService.findUser(authUser.getId()).orElse(null);
    Long candidateCount = userService.countUser();
    Long companyCount = companyService.countCompany();
    Long postCount = jobPostService.countJobPost();
    model.addAttribute("candidateCount", candidateCount > 100 ? "99+" : (candidateCount + "+"));
    model.addAttribute("companyCount", companyCount > 100 ? "99+" : (companyCount + "+"));
    model.addAttribute("postCount", postCount > 100 ? "99+" : (postCount + "+"));
    final Integer topCompanySize = 2;
    model.addAttribute("topCompanies", companyService.findTopCompanies(topCompanySize));
    final Integer topJobsSize = 5;
    model.addAttribute("topJobs", jobPostService.findTopJobPosts(topJobsSize));
    final Integer topCategorySize = 4;
    model.addAttribute("topCategories", jobCategoryService.findTopCategories(topCategorySize));
    final int searchSize = 6;
    Pageable pageable = PageRequest.of(page, searchSize);
    model.addAttribute("comp", companyLike);
    model.addAttribute("location", locationLike);
    model.addAttribute("post", postRelated);
    Page<JobPostResponseWithCompanyLogo> searchResult = null;
    if (!postRelated.isEmpty()) {
      searchResult = jobPostService.searchBy(pageable, postRelated);
    } else if (!companyLike.isEmpty()) {
      searchResult = jobPostService.searchBy(pageable, companyLike, "company");
    } else if (!locationLike.isEmpty()) {
      searchResult = jobPostService.searchBy(pageable, locationLike, "location");
    }
    if (searchResult != null) {
      model.addAttribute("searchResult", searchResult.getContent());
      model.addAttribute("searchTotalPage", searchResult.getTotalPages());
      model.addAttribute("searchCurrentPage", searchResult.getNumber());
    }
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
      Locale locale) {
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
      HttpSession session,
      RedirectAttributes redirectAttributes) {
    if (!user.getPassword().equals(user.getPasswordConfirm())) {
      String errorMessage = messageSource.getMessage("error.user.register.password.mismatch", null, locale);
      bindingResult.addError(
          new FieldError("user", "passwordConfirm", errorMessage));
      bindingResult.addError(
          new FieldError("user", "password", errorMessage));
    }
    if (bindingResult.hasErrors()) {
      model.addAttribute("roles", eUserRole.getLocalizedValues(messageSource, locale));
      return "public/register";
    }
    if (user.getRole().equals(eUserRole.RECRUITER) && mailVerification) {
      String _verificationCode = Optional.ofNullable(session.getAttribute("_verificationCode"))
          .map(String.class::cast)
          .orElse("");
      Long createdAt = (Long) session.getAttribute("createdAt");
      if (createdAt != null && System.currentTimeMillis() - createdAt > 60000) {
        session.invalidate();
        return "redirect:/register";
      }

      if (_verificationCode.isEmpty()) {
        _verificationCode = mailService.sendVerificationCode(user.getEmail());
        session.setAttribute("user", user);
        session.setAttribute("_verificationCode", _verificationCode);
        session.setAttribute("createdAt", System.currentTimeMillis());
      } else
        System.out.println("DEBUG:" + _verificationCode);
      return "public/confirm-email";
    }
    userService.insert(user);
    redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.success.register", null, locale));
    redirectAttributes.addFlashAttribute("type", "success");
    redirectAttributes.addFlashAttribute("translated", true);
    return "redirect:/login";
  }

  @PostMapping("/confirmation")
  public String confirmation(
      @RequestParam(defaultValue = "", required = false) String verifyCode,
      HttpSession session,
      Model model,
      Locale locale,
      RedirectAttributes redirectAttributes) {
    UserRegisterRequest user = (UserRegisterRequest) session.getAttribute("user");
    Long createdAt = (Long) session.getAttribute("createdAt");
    if (createdAt != null && System.currentTimeMillis() - createdAt > 60000) {
      session.invalidate();
      return "redirect:/register";
    }
    String _verificationCode = (String) session.getAttribute("_verificationCode");
    if (!verifyCode.equals(_verificationCode)) {
      model.addAttribute("incorrectVerifyCode", "Mã xác nhận không đúng");
      return "public/confirm-email";
    }
    session.invalidate();
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

  @GetMapping("/error")
  public String error() {
    return "public/error";
  }

  @GetMapping("/job/{id}")
  public String job(
      @PathVariable("id") Long jobId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      Model model,
      @AuthenticationPrincipal AuthUser authUser) {
    JobPostResponseWithCompanyLogo jobPost = jobPostService
        .findByIdWithCompanyLogo(jobId).orElseThrow(() -> new EntityNotFoundException("Job not found"));
    if (jobPost.getDeletedAt() != null &&
        (authUser == null || authUser.getRole().equals(eUserRole.JOB_SEEKER) ||
            (authUser.getRole().equals(eUserRole.RECRUITER) && authUser.getCompanyId() != jobPost.getCompanyId()))) {
      throw new AccessDeniedException("Job Has been removed");
    }
    model.addAttribute("job", jobPost);
    model.addAttribute(
        "category",
        jobCategoryService.find(jobPost.getCategory())
            .map(JobCategoryResponse::getName)
            .orElse(jobPost.getCategory().toString() + " - Undefined"));

    if (authUser != null) {
      if (authUser.getRole().equals(eUserRole.JOB_SEEKER)) {
        model.addAttribute("appliedApplication",
            applicationService.findApplication(jobId, authUser.getId()).orElse(null));
        Optional<JobFollowResponse> followOpt = followService.findFollowingJob(authUser.getId(), jobId);
        model.addAttribute("follow", followOpt.orElse(null));
      } else if (authUser.getRole().equals(eUserRole.RECRUITER)) {
        Pageable pageable = PageRequest.of(page, applicationPageSize);
        Page<ApplicationResponse> applications = applicationService.findByJobId(jobId, pageable);
        model.addAttribute("applicationsContent", applications.getContent());
        model.addAttribute("applicationsTotalPage", applications.getTotalPages());
        model.addAttribute("applicationsCurrentPage", applications.getNumber());
      }
    }
    return "public/job";
  }

  @GetMapping("/jobs")
  public String jobs(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "company", defaultValue = "0") Long companyId,
      @RequestParam(value = "category", defaultValue = "0") Long categoryId,
      Model model,
      Locale locale,
      HttpServletRequest servletReq) {
    Pageable pageable = PageRequest.of(page, jobPageSize);
    Page<JobPostResponseWithCompanyLogo> jobs;
    if (companyId != 0 || categoryId != 0) {
      if (companyId != 0) {
        jobs = jobPostService.findAllWithCompanyLogo(pageable, companyId, "company");
        model.addAttribute("searchBy",
            messageSource.getMessage("label.company", null, locale) + " " +
                companyService.findCompany(companyId)
                    .map(CompanyResponse::getCompanyName)
                    .orElse(companyId.toString()));
      } else {
        jobs = jobPostService.findAllWithCompanyLogo(pageable, categoryId, "category");
        model.addAttribute("searchBy",
            messageSource.getMessage("label.category", null, locale) + " " +
                jobCategoryService.find(categoryId)
                    .map(JobCategoryResponse::getName)
                    .orElse(categoryId.toString()));
      }
    } else {

      jobs = jobPostService.findAllWithCompanyLogo(pageable);
    }
    model.addAttribute("fullPath", PaginationUtil.getFullURL(servletReq));
    model.addAttribute("jobs", jobs.getContent());
    model.addAttribute("currentPage", jobs.getNumber());
    model.addAttribute("totalPage", jobs.getTotalPages());
    return "public/jobs";
  }

  @GetMapping("/companies")
  public String companies(
      @RequestParam(value = "page", defaultValue = "0") int page,
      Model model,
      HttpServletRequest servletReq) {
    Pageable pageable = PageRequest.of(page, companyPageSize);
    Page<CompanyResponse> companies = companyService.findAll(pageable);
    model.addAttribute("companies", companies.getContent());
    model.addAttribute("currentPage", companies.getNumber());
    model.addAttribute("totalPage", companies.getTotalPages());
    model.addAttribute("fullPath", PaginationUtil.getFullURL(servletReq));
    return "public/companies";
  }

  @GetMapping("company/{id}")
  public String company(
      @PathVariable("id") Long companyId,
      Model model,
      @AuthenticationPrincipal AuthUser authUser) {
    CompanyResponse company = companyService.findCompany(companyId)
        .orElseThrow(() -> new EntityNotFoundException("Company [" + companyId + "] not found"));
    model.addAttribute("company", company);
    model.addAttribute("totalJobs", jobPostService.countJobPost(companyId, true));
    model.addAttribute("activeJobs", jobPostService.countJobPost(companyId));
    if (authUser != null && authUser.getRole().equals(eUserRole.JOB_SEEKER)) {
      Optional<CompanyFollowResponse> followOpt = followService.findFollowingCompany(authUser.getId(), companyId);
      model.addAttribute("follow", followOpt.orElse(null));
    }
    return "public/company";
  }

  @GetMapping("download/cv/{pdfFileName}/{downloadName}")
  public ResponseEntity<Resource> downloadCV(
      @PathVariable("pdfFileName") String pdfFileName,
      @PathVariable("downloadName") String downloadName) {
    if (pdfFileName == null || !pdfFileName.endsWith(".pdf"))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    Path filePath = Paths.get(servletContext.getRealPath("/resources/"), uploadDir, cvDir).resolve(pdfFileName)
        .normalize();
    try {
      Resource resource = new UrlResource(filePath.toUri());
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadName + "\"")
          .body(resource);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/view-pdf/{pdfFileName}")
  public ResponseEntity<InputStreamResource> viewPDF(@PathVariable("pdfFileName") String pdfFileName) {
    if (pdfFileName == null || !pdfFileName.endsWith(".pdf"))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    Path filePath = Paths.get(servletContext.getRealPath("/resources/"), uploadDir, cvDir).resolve(pdfFileName)
        .normalize();
    InputStreamResource resource;
    try {
      resource = new InputStreamResource(new FileInputStream(filePath.toFile()));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(resource);
  }
}
