package asm02.service;

import asm02.dto.response.JobCategoryResponse;
import asm02.entity.JobCategory;

import java.util.List;
import java.util.Optional;

public interface JobCategoryService {
    List<JobCategoryResponse> findTopCategories(Integer size);
    List<JobCategoryResponse> findAll();
    Optional<JobCategoryResponse> find(Long id);
    Optional<JobCategory> getEntity(Long id);
}
