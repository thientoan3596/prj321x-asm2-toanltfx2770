package asm02.dao;

import asm02.entity.JobCategory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JobCategoryDaoImpl implements JobCategoryDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<JobCategory> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from JobCategory", JobCategory.class)
                .getResultList();
    }

    @Override
    public List<JobCategory> findTopCategories(Integer size) {
        final String sql = "SELECT * FROM TopCategoriesView";
        return sessionFactory.getCurrentSession().createNativeQuery(sql, JobCategory.class).setMaxResults(size).getResultList();
    }

    @Override
    public Optional<JobCategory> find(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from JobCategory where id = :id",JobCategory.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public void insert(JobCategory jobCategory) {
        sessionFactory.getCurrentSession().save(jobCategory);
    }

    @Override
    public void update(JobCategory jobCategory) {
        sessionFactory.getCurrentSession().update(jobCategory);
    }
}
