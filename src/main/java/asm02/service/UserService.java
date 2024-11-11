package asm02.service;

import asm02.dto.request.insert.UserRegisterRequest;
import asm02.dto.request.update.UserRequest;
import asm02.dto.response.UserResponse;
import asm02.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse update(UserRequest payload);
    UserResponse uploadAvatar(Long userId, MultipartFile file);
    UserResponse insert(UserRegisterRequest payload);
    Long countUser();

    //    void changePassword(long id, String newRawPassword);
    Optional<UserResponse> findUser(long id);
    Optional<User> getEntity(long id);
    List<UserResponse> findUsers();
}
