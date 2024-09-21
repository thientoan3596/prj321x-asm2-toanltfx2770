package asm02.dao;

import asm02.entity.Company;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompanyDaoImpl implements CompanyDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Company> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Company", Company.class)
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
                .createQuery("where id = :id", Company.class)
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
}
