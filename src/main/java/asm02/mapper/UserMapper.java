package asm02.mapper;

import asm02.dto.request.insert.UserRegisterRequest;
import asm02.dto.request.update.UserRequest;
import asm02.dto.response.UserResponse;
import asm02.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {CompanyMapper.class})
public interface UserMapper {
    UserResponse toResponse(User user);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "cvList", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt",expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "company",ignore = true)
    User toEntity(UserRegisterRequest payload);


    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "cvList", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "company",ignore = true)
    User toEntity(UserRequest payload);
}
