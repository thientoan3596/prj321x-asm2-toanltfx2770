package asm02.service;

import asm02.dao.UserDao;
import asm02.dto.request.insert.UserRegisterRequest;
import asm02.dto.request.update.UserRequest;
import asm02.dto.response.UserResponse;
import asm02.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserDao userDao;

    @Override
    public UserResponse update(UserRequest payload) {
        User user = userDao.findById(payload.getId()).orElseThrow(() -> new EntityNotFoundException("No such user with id: " + payload.getId()));
        user.merge(payload);
        userDao.update(user);
        return user.toResponse();
    }
    @Override
    public UserResponse uploadAvatar(Long userId, MultipartFile file) {
        User user = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("No such user with id: " + userId));
        String oldAvatar = user.getAvatar();
        String newAvatar = fileService.uploadFile(file);
        user.setAvatar(newAvatar);
        fileService.deleteFile(oldAvatar);
        return user.toResponse();
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
        return userDao.findById(id).map(User::toResponse);
    }

    @Override
    public Optional<User> findUserEntity(long id) {
        return userDao.findById(id);
    }

    @Override
    public List<UserResponse> findUsers() {
        throw new IllegalStateException("Method not implemented");
    }
}
