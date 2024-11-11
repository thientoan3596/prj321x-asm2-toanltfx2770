package asm02.mapper;

import asm02.dto.request.base.ApplicationBaseRequest;
import asm02.dto.response.ApplicationResponse;
import asm02.dto.response.ApplicationWithJobPostLogoResponse;
import asm02.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {JobPostMapper.class})
public interface ApplicationMapper {
    @Mapping(target = "cvId",expression = "java(application.getCv().getId())")
    @Mapping(target = "jobId",expression = "java(application.getJobPost().getId())")
    @Mapping(target = "userId",expression = "java(application.getUser().getId())")
    @Mapping(target = "cv",expression = "java(application.getCv().getFileName())")
    @Mapping(target = "user",expression = "java(application.getUser().getFullName())")
    @Mapping(target = "userAvatar",expression = "java(application.getUser().getAvatar())")
    @Mapping(target = "userEmail",expression = "java(application.getUser().getEmail())")
    ApplicationResponse toResponse(Application application);

    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "cv",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "jobPost",ignore = true)
    @Mapping(target = "user",ignore = true)
    @Mapping(target = "status",expression = "java(asm02.entity.eApplicationStatus.PENDING)")
    Application toEntity(ApplicationBaseRequest applicationRequest);
    @Mapping(target = "cvId",expression = "java(application.getCv().getId())")
    @Mapping(target = "jobId",expression = "java(application.getJobPost().getId())")
    @Mapping(target = "userId",expression = "java(application.getUser().getId())")
    @Mapping(target = "cv",expression = "java(application.getCv().getFileName())")
    @Mapping(target = "user",expression = "java(application.getUser().getFullName())")
    @Mapping(target = "userAvatar",expression = "java(application.getUser().getAvatar())")
    @Mapping(target = "userEmail",expression = "java(application.getUser().getEmail())")
    @Mapping(target = "post",source = "jobPost")
    ApplicationWithJobPostLogoResponse toResponseWithJobPostLogo(Application application);

}
