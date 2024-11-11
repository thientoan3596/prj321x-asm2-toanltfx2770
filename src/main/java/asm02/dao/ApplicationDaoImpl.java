package asm02.dao;

import asm02.entity.Application;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationDaoImpl implements ApplicationDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void insert(Application application) {
        sessionFactory.getCurrentSession()
                .save(application);
    }

    @Override
    public void update(Application application) {
        sessionFactory.getCurrentSession()
                .update(application);
    }

    @Override
    public Optional<Application> findOne(Long jobId, Long userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Application where jobPost.id = :jobId and user.id = :userId", Application.class)
                .setParameter("jobId", jobId)
                .setParameter("userId", userId)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<Application> findOne(Long applicationId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Application where id = :applicationId", Application.class)
                .setParameter("applicationId", applicationId)
                .getResultList().stream().findFirst();
    }

    @Override
    public Page<Application> findByJobId(Long jobId,Pageable pageable) {
        String query =  "from Application where jobPost.id = :jobId order by id desc";
        List<Application> applications = sessionFactory.getCurrentSession()
                .createQuery(query, Application.class)
                .setParameter("jobId",jobId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long total = sessionFactory.getCurrentSession()
                .createQuery("select count (*) "+query, Long.class)
                .setParameter("jobId",jobId)
                .getSingleResult();
        return new PageImpl<>(applications, pageable, total);
    }

    @Override
    public Page<Application> findByUserId(Long userId, Pageable pageable) {
        String query =  "from Application where user.id = :userId order by id asc";
        List<Application> applications = sessionFactory.getCurrentSession()
                .createQuery(query, Application.class)
                .setParameter("userId",userId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long total = sessionFactory.getCurrentSession()
                .createQuery("select count (*) "+query, Long.class)
                .setParameter("userId",userId)
                .getSingleResult();
        return new PageImpl<>(applications, pageable, total);
    }
}
