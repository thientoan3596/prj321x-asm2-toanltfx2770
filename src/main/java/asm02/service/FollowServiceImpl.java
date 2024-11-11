package asm02.service;

import asm02.dao.FollowDao;
import asm02.dto.request.base.CompanyFollowRequest;
import asm02.dto.request.base.JobFollowRequest;
import asm02.dto.response.ApplicationResponse;
import asm02.dto.response.CompanyFollowResponse;
import asm02.dto.response.JobFollowResponse;
import asm02.entity.*;
import asm02.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowDao followDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobPostService jobPostService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private FollowMapper followMapper;

    @Override
    public CompanyFollowResponse insert(CompanyFollowRequest payload) {
        Optional<CompanyFollow> followOpt = followDao.findFollowingCompany(payload.getUserId(), payload.getCompanyId());
        if (followOpt.isPresent()) {
            CompanyFollow follow = followOpt.get();
            if (!follow.getActive()) {
                follow.setActive(true);
                followDao.update(follow);
            }
            return followMapper.toResponse(follow);
        }
        User follower = userService.getEntity(payload.getUserId()).orElseThrow(() -> new EntityNotFoundException("No such user with id: " + payload.getUserId()));
        Company company = companyService.getEntity(payload.getCompanyId()).orElseThrow(() -> new EntityNotFoundException("No such company with id: " + payload.getCompanyId()));
        CompanyFollow follow = CompanyFollow.builder()
                .user(follower)
                .company(company)
                .build();
        followDao.insert(follow);
        return followMapper.toResponse(follow);
    }

    @Override
    public JobFollowResponse insert(JobFollowRequest payload) {
        Optional<JobFollow> followOpt = followDao.findFollowingJob(payload.getUserId(), payload.getJobPostId());
        if (followOpt.isPresent()) {
            JobFollow follow = followOpt.get();
            if (!follow.getActive()) {
                follow.setActive(true);
                followDao.update(follow);
            }
            return followMapper.toResponse(follow);
        }
        User follower = userService.getEntity(payload.getUserId()).orElseThrow(() -> new EntityNotFoundException("No such user with id: " + payload.getUserId()));
        JobPost jobPost = jobPostService.getEntity(payload.getJobPostId()).orElseThrow(() -> new EntityNotFoundException("No such jobPost with id: " + payload.getJobPostId()));
        JobFollow follow = JobFollow.builder()
                .user(follower)
                .jobPost(jobPost)
                .build();
        followDao.insert(follow);
        return followMapper.toResponse(follow);
    }

    @Override
    public void unfollowCompany(Long followId) {
        CompanyFollow follow = followDao.findFollowingCompany(followId).orElseThrow(() -> new EntityNotFoundException("Follow not found"));
        follow.setActive(false);
        followDao.update(follow);
    }

    @Override
    public void unfollowJob(Long followId) {
        JobFollow follow = followDao.findFollowingJob(followId).orElseThrow(() -> new EntityNotFoundException("Follow not found"));
        follow.setActive(false);
        followDao.update(follow);
    }

    @Override
    public Page<CompanyFollowResponse> findFollowingCompanies(Long userId, Pageable pageable) {
        return followDao.findFollowingCompanies(userId, pageable).map(followMapper::toResponse);
    }

    @Override
    public Page<JobFollowResponse> findFollowingJobs(Long userId, Pageable pageable) {
//        return followDao.findFollowingJobs(userId,pageable).map(followMapper::toResponse);
//        Page<JobFollow> jobFollows = followDao.findFollowingJobs(userId, pageable);
//        Page<JobFollowResponse> jobFollowResponses = jobFollows.map(followMapper::toResponse);
//        jobFollowResponses.forEach(f -> f.setApplication(applicationService.findApplication(userId, f.getJobId()).orElse(null)));
//        return jobFollowResponses;
        return followDao.findFollowingJobs(userId, pageable).map(this::toResponse);
    }

    @Override
    public Optional<CompanyFollowResponse> findFollowingCompany(Long userId, Long companyId) {
        return followDao.findFollowingCompany(userId, companyId).map(followMapper::toResponse);
    }

    @Override
    public Optional<JobFollowResponse> findFollowingJob(Long userId, Long jobId) {
//        return followDao.findFollowingJob(userId, jobId).map(followMapper::toResponse);
        return followDao.findFollowingJob(userId,jobId).map(this::toResponse);
    }

    private JobFollowResponse toResponse(JobFollow jobFollow) {
        JobFollowResponse follow = followMapper.toResponse(jobFollow);
        follow.setApplication(
                applicationService.findApplication(
                        jobFollow.getJobPost().getId(),
                        jobFollow.getUser().getId()
                ).orElse(
                        ApplicationResponse.builder()
                                .jobId(jobFollow.getJobPost().getId())
                                .userId(jobFollow.getUser().getId())
                                .build()
                )
        );
        return follow;
    }

}
