package asm02.service;

import asm02.dao.UserDao;
import asm02.dto.UserRegisterRequest;
import asm02.dto.UserRequest;
import asm02.dto.UserResponse;
import asm02.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;

    @Override
    public UserResponse update(UserRequest payload) {
        return null;
    }

    @Override
    public UserResponse insert(UserRegisterRequest payload) {
        User user = payload.toEntity();
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        userDao.insert(user);
        return user.toResponse();
    }

    @Override
    public Optional<UserResponse> findUser(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserEntity(long id) {
        return Optional.empty();
    }

    @Override
    public List<UserResponse> findUsers() {
        return null;
    }
}
