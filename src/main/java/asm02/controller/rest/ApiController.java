package asm02.controller.rest;

import asm02.dto.request.base.ApplicationBaseRequest;
import asm02.dto.response.ApplicationResponse;
import asm02.dto.response.CVResponse;
import asm02.security.AuthUser;
import asm02.service.ApplicationService;
import asm02.service.CVService;
import asm02.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private Validator validator;
    @Autowired
    private CVService cvService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private MessageSource messageSource;


    //region CV
    @PreAuthorize("hasRole('ROLE_JOB_SEEKER')")
    @GetMapping("/cv-list")
    public ResponseEntity<List<CVResponse>> getCVList(@AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.ok(cvService.findByUserId(authUser.getId()));
    }

    //endregion
    //region Job
    @PreAuthorize("hasRole('ROLE_JOB_SEEKER')")
    @PostMapping("/apply-job/{id}")
    public ResponseEntity<?> applyForJob(
            @PathVariable("id") Long jobId,
            @RequestParam("cvId") Long cvId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("text") String text,
            @AuthenticationPrincipal AuthUser authUser,
            Locale locale
    ) {
        ApplicationBaseRequest applicationRequest = new ApplicationBaseRequest();
        applicationRequest.setText(text.trim());
        applicationRequest.setUserId(authUser.getId());
        applicationRequest.setJobId(jobId);
        applicationRequest.setCvId(cvId);
        applicationRequest.setFile(!(file == null || file.isEmpty()));
        BindingResult bindingResult = new BeanPropertyBindingResult(applicationRequest, "applicationRequest");
        validator.validate(applicationRequest, bindingResult);
        if (cvId == -1 ){
            if(file.isEmpty() || file == null){
                bindingResult.rejectValue("file", null, messageSource.getMessage("message.error.empty-file", null, locale));
            }else if (!fileService.isCv(file)) {
                bindingResult.rejectValue("file", null, messageSource.getMessage("message.error.not-cv-file", null, locale));
            } else {
                CVResponse uploadedCv = cvService.uploadCv(file, authUser.getId(), false);
                applicationRequest.setCvId(uploadedCv.getId());
            }
        }
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        ApplicationResponse application = applicationService.createApplication(applicationRequest);
        HashMap<String, Object> response = new HashMap<>();
        response.put("application", application);
        response.put("message", messageSource.getMessage("message.success.job-applied", null, locale));
        response.put("applicationStatus", messageSource.getMessage("label." + application.getStatus().toString().toLowerCase(), null, locale));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //endregion
}
