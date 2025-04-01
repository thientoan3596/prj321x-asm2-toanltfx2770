package asm02.mapper;

import asm02.dto.request.insert.JobPostInsertRequest;
import asm02.dto.request.update.JobPostRequest;
import asm02.dto.response.JobPostResponse;
import asm02.dto.response.JobPostResponseWithCompanyLogo;
import asm02.entity.JobPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobPostMapper {
    @Mapping(target = "category", expression = "java(jobPost.getJobCategory().getId())")
    @Mapping(target = "company", expression = "java(jobPost.getCompany().getCompanyName())")
    @Mapping(target = "companyId", expression = "java(jobPost.getCompany().getId())")
    JobPostResponse toResponse(JobPost jobPost);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jobCategory", ignore = true)
    @Mapping(target = "company",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    JobPost toEntity(JobPostInsertRequest payload);

    JobPostRequest toRequest(JobPostResponse response);
    @Mapping(target = "companyLogo",expression = "java(jobPost.getCompany().getLogo())")
    @Mapping(target = "category", expression = "java(jobPost.getJobCategory().getId())")
    @Mapping(target = "company", expression = "java(jobPost.getCompany().getCompanyName())")
    @Mapping(target = "companyId", expression = "java(jobPost.getCompany().getId())")
    JobPostResponseWithCompanyLogo toResponseWithCompanyLogo(JobPost jobPost);
}
