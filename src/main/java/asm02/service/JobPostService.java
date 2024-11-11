package asm02.service;

import asm02.dto.request.insert.JobPostInsertRequest;
import asm02.dto.request.update.JobPostRequest;
import asm02.dto.response.JobPostResponse;
import asm02.dto.response.JobPostResponseWithCompanyLogo;
import asm02.entity.JobPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobPostService {
    Long countJobPost();
    Long countJobPost(Long companyId,boolean includeClosed);

    /**
     * Default method for counting job posts where includeClosed is false
     */
    Long countJobPost(Long companyId);
    List<JobPostResponseWithCompanyLogo> findTopJobPosts(Integer size);
    Page<JobPostResponse> findAll(Pageable pageable);
    Page<JobPostResponse> findAll(Pageable pageable,Long companyId);
    Page<JobPostResponseWithCompanyLogo> findAllWithCompanyLogo(Pageable pageable);
    Page<JobPostResponseWithCompanyLogo> findAllWithCompanyLogo(Pageable pageable,Long targetId,String targetField);
    Page<JobPostResponseWithCompanyLogo> searchBy(Pageable pageable, String keyword,String byField);
    Page<JobPostResponseWithCompanyLogo> searchBy(Pageable pageable, String keyword);
    Page<JobPostResponse> findJobsPosted(Long companyId, Pageable pageable);
    Optional<JobPostResponse> find(Long id);
    Optional<JobPostResponseWithCompanyLogo> findByIdWithCompanyLogo(Long id);
    Optional<JobPost> getEntity(Long id);
    JobPostResponse insert (JobPostInsertRequest payload);
    JobPostResponse update (JobPostRequest payload);
}
