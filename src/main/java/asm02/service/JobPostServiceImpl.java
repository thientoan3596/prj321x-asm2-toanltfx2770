package asm02.service;

import asm02.dao.JobPostDao;
import asm02.dto.request.insert.JobPostInsertRequest;
import asm02.dto.request.update.JobPostRequest;
import asm02.dto.response.JobPostResponse;
import asm02.dto.response.JobPostResponseWithCompanyLogo;
import asm02.entity.Company;
import asm02.entity.JobCategory;
import asm02.entity.JobPost;
import asm02.mapper.JobPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobPostServiceImpl implements JobPostService {

    @Autowired
    private JobPostDao jobPostDao;
    @Autowired
    private JobPostMapper jobPostMapper;
    @Autowired
    private JobCategoryService jobCategoryService;
    @Autowired
    private CompanyService companyService;
    @Override
    public Long countJobPost(){
        return jobPostDao.countJobPost();
    }

    @Override
    public Long countJobPost(Long companyId, boolean includeClosed) {
        return jobPostDao.countJobPost(companyId,includeClosed);
    }
    @Override
    public Long countJobPost(Long companyId) {
        return countJobPost(companyId,false);
    }
    @Override
    public List<JobPostResponseWithCompanyLogo> findTopJobPosts(Integer size){
        return jobPostDao.findTopJobPosts(size).stream().map(jobPostMapper::toResponseWithCompanyLogo).collect(Collectors.toList());
    }



    @Override
    public Page<JobPostResponse> findAll(Pageable pageable) {
        return jobPostDao.findAll(pageable).map(jobPostMapper::toResponse);
    }
    @Override
    public Page<JobPostResponse> findAll(Pageable pageable,Long companyId) {
        return jobPostDao.findAll(pageable,companyId,"company").map(jobPostMapper::toResponse);
    }

    @Override
    public Page<JobPostResponseWithCompanyLogo> findAllWithCompanyLogo(Pageable pageable){
        return jobPostDao.findAll(pageable).map(jobPostMapper::toResponseWithCompanyLogo);
    }

    @Override
    public Page<JobPostResponseWithCompanyLogo> findAllWithCompanyLogo(Pageable pageable,Long targetId,String targetField){
        return jobPostDao.findAll(pageable,targetId,targetField).map(jobPostMapper::toResponseWithCompanyLogo);
    }


    @Override
    public Page<JobPostResponseWithCompanyLogo> searchBy(Pageable pageable, String keyword,String byField){
        return jobPostDao.searchBy(pageable,keyword,byField).map(jobPostMapper::toResponseWithCompanyLogo);

    }
    @Override
    public Page<JobPostResponseWithCompanyLogo> searchBy(Pageable pageable, String keyword){
        return searchBy(pageable,keyword,"");
    }
    @Override
    public Page<JobPostResponse> findJobsPosted(Long companyId, Pageable pageable){
        return jobPostDao.findByCompanyId(companyId,pageable).map(jobPostMapper::toResponse);
    }

    @Override
    public Optional<JobPostResponse> find(Long id) {
        return jobPostDao.find(id).map(jobPostMapper::toResponse);
    }
    @Override
    public Optional<JobPostResponseWithCompanyLogo> findByIdWithCompanyLogo(Long id){
        return jobPostDao.find(id).map(jobPostMapper::toResponseWithCompanyLogo);
    }

    @Override
    public Optional<JobPost> getEntity(Long id){
        return jobPostDao.find(id);
    }
    @Override
    public JobPostResponse insert(JobPostInsertRequest payload) {
        JobCategory  category = jobCategoryService.getEntity(payload.getCategory()).orElseThrow(() -> new EntityNotFoundException("No such category with id: " + payload.getCategory()));
        Company comp = companyService.getEntity(payload.getCompanyId()).orElseThrow(()-> new EntityNotFoundException("No such company with id: " + payload.getCompanyId()));
        JobPost jobPost = jobPostMapper.toEntity(payload);
        jobPost.setCompany(comp);
        jobPost.setJobCategory(category);
        jobPostDao.insert(jobPost);
        return jobPostMapper.toResponse(jobPost);
    }
    @Override
    public JobPostResponse update (JobPostRequest payload){
        JobPost jobPost = getEntity(payload.getId()).orElseThrow(()->new EntityNotFoundException("No such job with id: " + payload.getId()));
        jobPost.merge(payload);
        if(!Objects.equals(jobPost.getJobCategory().getId(), payload.getCategory())){
            JobCategory  category = jobCategoryService.getEntity(payload.getCategory()).orElseThrow(() -> new EntityNotFoundException("No such category with id: " + payload.getCategory()));
            jobPost.setJobCategory(category);
        }
        jobPostDao.update(jobPost);
        return jobPostMapper.toResponse(jobPost);
    }
}
