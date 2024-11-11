package asm02.service;

import asm02.dto.request.base.CompanyFollowRequest;
import asm02.dto.request.base.JobFollowRequest;
import asm02.dto.response.CompanyFollowResponse;
import asm02.dto.response.JobFollowResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FollowService {
    CompanyFollowResponse insert(CompanyFollowRequest payload);
    JobFollowResponse insert(JobFollowRequest payload);
    void unfollowCompany(Long followId);
    void unfollowJob(Long followId);
    Page<CompanyFollowResponse> findFollowingCompanies(Long userId, Pageable pageable);
    Page<JobFollowResponse> findFollowingJobs(Long userId, Pageable pageable);
    Optional<CompanyFollowResponse> findFollowingCompany(Long userId, Long companyId);
    Optional<JobFollowResponse> findFollowingJob(Long userId, Long jobId);
}

