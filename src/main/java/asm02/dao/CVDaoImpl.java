package asm02.dao;

import asm02.entity.CV;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CVDaoImpl implements CVDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void insert(CV cv) {
        sessionFactory.getCurrentSession().save(cv);
    }

    @Override
    public void delete(CV cv) {
        sessionFactory.getCurrentSession().delete(cv);
    }

    @Override
    public List<CV> findByUserId(Long userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from CV where user.id = :userId", CV.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * Update ALL of user's CVs to not default
     */
    @Override
    public void updateUserCVsToUnDefault(Long userId) {
        sessionFactory.getCurrentSession()
                .createQuery("update CV set isDefault = false where user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public Optional<CV> findById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from CV where id = :id",CV.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
}
