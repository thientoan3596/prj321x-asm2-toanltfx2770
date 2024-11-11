package asm02.dao;

import asm02.entity.JobCategory;

import java.util.List;
import java.util.Optional;

public interface JobCategoryDao {

    List<JobCategory> findAll();

    List<JobCategory> findTopCategories(Integer size);
    Optional<JobCategory> find(Long id);
    void insert(JobCategory jobCategory);
    void update(JobCategory jobCategory);
}
