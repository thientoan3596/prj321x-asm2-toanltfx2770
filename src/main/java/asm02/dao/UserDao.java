package asm02.dao;

import asm02.entity.User;
import asm02.security.AuthUser;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll();
    Optional<User> findById(long id);
    Optional<AuthUser> findByEmail(String email);
    void insert(User user);
    void insertRecruiter(User recruiter);
    void update(User user);
}
