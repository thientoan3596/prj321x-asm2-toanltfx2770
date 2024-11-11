package asm02.dao;

import asm02.entity.JobPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobPostDao {
    Long countJobPost();
    Long countJobPost(Long companyId, boolean includeClosed);
    Page<JobPost> findAll(Pageable pageable, Long targetId, String targetField) ;
    Page<JobPost> findAll(Pageable pageable) ;
    Page<JobPost> findByCompanyId(Long companyId, Pageable pageable);
    Optional<JobPost> find(Long id);
    void insert(JobPost jobPost);
    void update(JobPost jobPost);

    List<JobPost> findTopJobPosts(Integer size);

    Page<JobPost> searchBy(Pageable pageable, String keyword, String byField);
}
