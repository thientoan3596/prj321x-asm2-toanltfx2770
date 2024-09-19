package asm02.dao;

import asm02.entity.User;
import asm02.security.AuthUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<User> findAll() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from User where deletedAt is null", User.class)
                .getResultList();
    }

    @Override
    public Optional<AuthUser> findByEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("from AuthUser where email = :email and deletedAt is null", AuthUser.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
    @Override
    public Optional<User> findById(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from User where id = :id and deletedAt is null", User.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public void insert(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }
}
