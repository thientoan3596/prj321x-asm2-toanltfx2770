package asm02.dao;

import asm02.entity.CompanyFollow;
import asm02.entity.JobFollow;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FollowDaoImpl implements FollowDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void insert(CompanyFollow follow) {
        sessionFactory.getCurrentSession()
                .save(follow);
    }
    @Override
    public void insert(JobFollow follow){
        sessionFactory.getCurrentSession()
                .save(follow);
    }

    @Override
    public void update(CompanyFollow follow) {
        sessionFactory.getCurrentSession()
                .update(follow);
    }

    @Override
    public void update(JobFollow follow) {
        sessionFactory.getCurrentSession()
                .update(follow);
    }
    @Override
    public Optional<CompanyFollow> findFollowingCompany(Long followId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from CompanyFollow where type = 'COMPANY' and id = :followId", CompanyFollow.class)
                .setParameter("followId", followId)
                .getResultList().stream().findFirst();
    }
    @Override
    public Optional<CompanyFollow> findFollowingCompany(Long userId, Long companyId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from CompanyFollow where type = 'COMPANY' and user.id = :userId and company.id = :companyId", CompanyFollow.class)
                .setParameter("userId", userId)
                .setParameter("companyId", companyId)
                .getResultList().stream().findFirst();
    }
    @Override
    public Page<CompanyFollow> findFollowingCompanies(Long userId, Pageable pageable) {
       String query =  "from CompanyFollow where type = 'COMPANY' and  user.id = :userId and active = true order by id asc";
       List<CompanyFollow> follows = sessionFactory.getCurrentSession()
               .createQuery(query, CompanyFollow.class)
               .setParameter("userId",userId)
               .setFirstResult((int) pageable.getOffset())
               .setMaxResults(pageable.getPageSize())
               .getResultList();
       Long total = sessionFactory.getCurrentSession()
               .createQuery("select count (*) "+query, Long.class)
               .setParameter("userId",userId)
               .getSingleResult();
       return new PageImpl<>(follows, pageable, total);
    }

    @Override
    public Optional<JobFollow> findFollowingJob(Long followId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from JobFollow where type = 'JOB_POST' and id = :followId", JobFollow.class)
                .setParameter("followId", followId)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<JobFollow> findFollowingJob(Long userId, Long jobId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from JobFollow where type = 'JOB_POST' and user.id = :userId and jobPost.id = :jobId", JobFollow.class)
                .setParameter("userId", userId)
                .setParameter("jobId", jobId)
                .getResultList().stream().findFirst();
    }
    @Override
    public Page<JobFollow> findFollowingJobs(Long userId, Pageable pageable) {
        String query =  "from JobFollow where type = 'JOB_POST' and user.id = :userId and active = true order by id asc";
        List<JobFollow> follows = sessionFactory.getCurrentSession()
                .createQuery(query, JobFollow.class)
                .setParameter("userId",userId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long total = sessionFactory.getCurrentSession()
                .createQuery("select count (*) "+query, Long.class)
                .setParameter("userId",userId)
                .getSingleResult();
        return new PageImpl<>(follows, pageable, total);
    }
}
