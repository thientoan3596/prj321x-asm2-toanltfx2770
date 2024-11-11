package asm02.service;

import asm02.dto.request.base.ApplicationBaseRequest;
import asm02.dto.response.ApplicationResponse;
import asm02.dto.response.ApplicationWithJobPostLogoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationService {
    ApplicationResponse createApplication(ApplicationBaseRequest applicationBaseRequest);
    Optional<ApplicationResponse> findApplication(Long jobId, Long userId);
    Optional<ApplicationResponse> findApplication(Long applicationId);
    Page<ApplicationResponse> findByJobId(Long jobId, Pageable pageable);
    Page<ApplicationResponse> findByUserId(Long userId, Pageable pageable);
    Page<ApplicationWithJobPostLogoResponse> findByUserId_JobPostWithLogo(Long userId, Pageable pageable);
}
