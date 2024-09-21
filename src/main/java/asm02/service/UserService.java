package asm02.service;

import asm02.dto.UserRegisterRequest;
import asm02.dto.UserRequest;
import asm02.dto.UserResponse;
import asm02.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse update(UserRequest payload);
    UserResponse uploadAvatar(Long userId, MultipartFile file);
    UserResponse insert(UserRegisterRequest payload);
//    void changePassword(long id, String newRawPassword);
    Optional<UserResponse> findUser(long id);
    Optional<User> findUserEntity(long id);
    List<UserResponse> findUsers();
}
