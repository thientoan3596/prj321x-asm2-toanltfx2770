package asm02.dao;

import asm02.entity.JobPost;
import asm02.util.Sanitizer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JobPostDaoImpl implements JobPostDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long countJobPost() {
        return sessionFactory.getCurrentSession()
                .createQuery("select count(*) from JobPost", Long.class)
                .getSingleResult();
    }

    @Override
    public Long countJobPost(Long companyId, boolean includeClosed) {
        String query = "select count(*) from JobPost where company.id = :companyId";
        if (!includeClosed) {
            query += " and deadline >= now()";
        }
        return sessionFactory.getCurrentSession()
                .createQuery(query, Long.class)
                .setParameter("companyId", companyId)
                .getSingleResult();
    }

    @Override
    public Page<JobPost> findAll(Pageable pageable, Long targetId, String targetField) {
        String query = "from JobPost where deadline >= now() ";
        if (targetField.equals("company")) {
            query += " and company.id = :targetId";
        } else if (targetField.equals("category")) {
            query += " and jobCategory.id = :targetId";
        } else throw new IllegalArgumentException("Un-support target field " + targetField);
        query += "  order by id desc";
        List<JobPost> posts = sessionFactory.getCurrentSession()
                .createQuery(query, JobPost.class)
                .setParameter("targetId", targetId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long total = sessionFactory.getCurrentSession()
                .createQuery("select count(*)" + query, Long.class)
                .setParameter("targetId", targetId)
                .getSingleResult();
        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Page<JobPost> findAll(Pageable pageable) {
        String query = "from JobPost where deadline >= now() ";
        query += "  order by id desc";
        List<JobPost> posts = sessionFactory.getCurrentSession()
                .createQuery(query, JobPost.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Long total = sessionFactory.getCurrentSession()
                .createQuery("select count(*)" + query, Long.class)
                .getSingleResult();
        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Page<JobPost> findByCompanyId(Long companyId, Pageable pageable) {
        List<JobPost> posts = sessionFactory.getCurrentSession()
                .createQuery("from JobPost where company.id = :companyId order by id desc", JobPost.class)
                .setParameter("companyId", companyId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        long total = sessionFactory.getCurrentSession()
                .createQuery("select count(*) from JobPost where company.id = :companyId", Long.class)
                .setParameter("companyId", companyId)
                .getSingleResult();
        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Optional<JobPost> find(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from JobPost where id = :id", JobPost.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();

    }

    @Override
    public void insert(JobPost jobPost) {
        sessionFactory.getCurrentSession().save(jobPost);
    }

    @Override
    public void update(JobPost jobPost) {
        sessionFactory.getCurrentSession().update(jobPost);
    }

    @Override
    public List<JobPost> findTopJobPosts(Integer size) {
        final String sql = "SELECT * FROM TopJobsView";
        return sessionFactory.getCurrentSession().createNativeQuery(sql, JobPost.class).setMaxResults(size).getResultList();
    }

    @Override
    public Page<JobPost> searchBy(Pageable pageable, String keyword, String byField) {
        final String sanitizedKw = Sanitizer.sanitizeQuery(keyword);
        String HQL = "from JobPost where deadline >= now() ";
        if (byField.equals("company")) {
            HQL += "and company.companyName like :keyword order by length(company.companyName) - length(replace(title, :keyword, '')) desc, id desc";
        } else if (byField.equals("location")) {
            HQL += "and address like :keyword order  by id desc";
        } else {
            HQL += "and (title like :keyword or description like :keyword or jobCategory.name like :keyword) " +
                    "order by " +
                    "case " +
                    "  when jobCategory.name like :keyword then 1 " +
                    "  when title like :keyword then 2 " +
                    "  when description like :keyword then 3 " +
                    "  else 4 end, id desc";
        }
        List<JobPost> posts = sessionFactory.getCurrentSession()
                .createQuery(HQL, JobPost.class)
                .setParameter("keyword", "%" + sanitizedKw + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long total = sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(*) " + HQL, Long.class)
                .setParameter("keyword", "%" + sanitizedKw + "%")
                .getSingleResult();
        return new PageImpl<>(posts, pageable, total);
    }

}
