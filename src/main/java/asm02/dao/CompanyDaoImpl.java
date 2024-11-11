package asm02.dao;

import asm02.entity.Company;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompanyDaoImpl implements CompanyDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long countCompany() {
        return sessionFactory.getCurrentSession()
                .createQuery("select count(*) from Company", Long.class)
                .getSingleResult();
    }

    @Override
    public List<Company> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Company", Company.class)
                .getResultList();
    }

    @Override
    public List<Company> findTopCompanies(Integer size) {
        final String sql = "SELECT * FROM TopCompaniesView";
        return sessionFactory.getCurrentSession().createNativeQuery(sql, Company.class).setMaxResults(size).getResultList();
    }

    @Override
    public List<Company> findCompaniesByName(List<String> names) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Company where companyName in (:names)", Company.class)
                .setParameter("names", names)
                .getResultList();
    }

    @Override
    public List<Company> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Company where name = :name", Company.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Optional<Company> findById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Company where id = :id", Company.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public void insert(Company company) {
        sessionFactory.getCurrentSession().save(company);
    }

    @Override
    public void update(Company company) {
        sessionFactory.getCurrentSession().update(company);
    }

    @Override
    public Optional<Company> findByRecruiter(long recruiterId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Company where recruiter.id = :id and deletedAt is null", Company.class)
                .setParameter("id", recruiterId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        final String query = "from Company order by companyName asc, id desc";
        List<Company> companies = sessionFactory.getCurrentSession()
                .createQuery(query, Company.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Long total = sessionFactory.getCurrentSession()
                .createQuery("select count (*) " + query, Long.class)
                .getSingleResult();
        return new PageImpl<>(companies, pageable, total);
    }
}
