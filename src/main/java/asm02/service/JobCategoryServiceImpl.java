package asm02.service;

import asm02.dao.JobCategoryDao;
import asm02.dto.response.JobCategoryResponse;
import asm02.entity.JobCategory;
import asm02.mapper.JobCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobCategoryServiceImpl  implements  JobCategoryService{
    @Autowired
    private JobCategoryDao jobCategoryDao;
    @Autowired
    private JobCategoryMapper jobCategoryMapper;

    @Override
    public List<JobCategoryResponse> findTopCategories(Integer size) {
        return jobCategoryDao.findTopCategories(size).stream().map(jobCategoryMapper::toTopCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public List<JobCategoryResponse> findAll() {
        return jobCategoryDao.findAll().stream().map(jobCategoryMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<JobCategoryResponse> find(Long id) {
        return jobCategoryDao.find(id).map(jobCategoryMapper::toResponse);
    }
    @Override
    public Optional<JobCategory> getEntity(Long id){
        return jobCategoryDao.find(id);
    }
}
