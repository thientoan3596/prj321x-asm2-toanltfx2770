package asm02.dao;

import asm02.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationDao {
    void insert(Application application);
    void update(Application application);
    Optional<Application> findOne(Long jobId, Long userId);
    Optional<Application> findOne(Long applicationId);
    Page<Application> findByJobId(Long jobId, Pageable pageable);
    Page<Application> findByUserId(Long userId,Pageable pageable);
}
