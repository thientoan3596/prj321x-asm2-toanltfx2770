package asm02.dao;

import asm02.entity.CompanyFollow;
import asm02.entity.JobFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FollowDao {
    void insert(CompanyFollow follow);
    void insert(JobFollow follow);
    void update(CompanyFollow follow);
    void update(JobFollow follow);
    /*
     * non-active included
     */
    Optional<CompanyFollow> findFollowingCompany(Long followId);
    /*
     * non-active included
     */
    Optional<CompanyFollow> findFollowingCompany(Long userId, Long companyId);
    Page<CompanyFollow> findFollowingCompanies(Long userId, Pageable pageable);
    /*
     * non-active included
     */
    Optional<JobFollow> findFollowingJob(Long followId);
    Optional<JobFollow> findFollowingJob(Long userId, Long jobId);
    Page<JobFollow> findFollowingJobs(Long userId, Pageable pageable);
}
