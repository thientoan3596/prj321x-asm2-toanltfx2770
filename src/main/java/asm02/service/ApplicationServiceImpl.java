package asm02.service;

import asm02.dao.ApplicationDao;
import asm02.dto.request.base.ApplicationBaseRequest;
import asm02.dto.response.ApplicationResponse;
import asm02.dto.response.ApplicationWithJobPostLogoResponse;
import asm02.entity.Application;
import asm02.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private JobPostService jobPostService;
    @Autowired
    private CVService cvService;
    @Override
    public ApplicationResponse createApplication(
            ApplicationBaseRequest applicationBaseRequest
    ) {
        Application application = applicationMapper.toEntity(applicationBaseRequest);
        application.setUser(userService.getEntity(applicationBaseRequest.getUserId()).orElseThrow(()->new EntityNotFoundException("User not found ["+applicationBaseRequest.getUserId()+"]")));
        application.setJobPost(jobPostService.getEntity(applicationBaseRequest.getJobId()).orElseThrow(()->new EntityNotFoundException("Job not found ["+applicationBaseRequest.getJobId()+"]")));
        application.setCv(cvService.getEntity(applicationBaseRequest.getCvId()).orElseThrow(()->new EntityNotFoundException("CV not found ["+applicationBaseRequest.getCvId()+"]")));
        applicationDao.insert(application);
        return applicationMapper.toResponse(application);
    }
    @Override
    public Optional<ApplicationResponse> findApplication(Long jobId, Long userId){
        return applicationDao.findOne(jobId, userId).map(applicationMapper::toResponse);
    }

    @Override
    public Optional<ApplicationResponse> findApplication(Long applicationId) {
        return applicationDao.findOne(applicationId).map(applicationMapper::toResponse);
    }

    @Override
    public Page<ApplicationResponse> findByJobId(Long jobId, Pageable pageable){
        return applicationDao.findByJobId(jobId,pageable).map(applicationMapper::toResponse);
    }
    @Override
    public Page<ApplicationResponse> findByUserId(Long userId,Pageable pageable){
        return applicationDao.findByUserId(userId,pageable).map(applicationMapper::toResponse);
    }
    @Override
    public Page<ApplicationWithJobPostLogoResponse> findByUserId_JobPostWithLogo(Long userId, Pageable pageable){
        return applicationDao.findByUserId(userId,pageable).map(applicationMapper::toResponseWithJobPostLogo);
    }

}
