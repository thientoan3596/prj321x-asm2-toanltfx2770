package asm02.mapper;


import asm02.dto.response.CompanyFollowResponse;
import asm02.dto.response.JobFollowResponse;
import asm02.entity.CompanyFollow;
import asm02.entity.JobFollow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    @Mapping(target = "companyId",expression = "java(follow.getCompany().getId())")
    @Mapping(target = "companyName",expression = "java(follow.getCompany().getCompanyName())")
    @Mapping(target = "companyLogo",expression = "java(follow.getCompany().getLogo())")
    @Mapping(target = "companyAddress",expression = "java(follow.getCompany().getAddress())")
    @Mapping(target = "companyEmail",expression = "java(follow.getCompany().getEmail())")
    @Mapping(target = "userId",expression = "java(follow.getUser().getId())")
    @Mapping(target = "active",expression = "java(follow.getActive())")
    CompanyFollowResponse toResponse(CompanyFollow follow);

    @Mapping(target = "companyName",expression = "java(follow.getJobPost().getCompany().getCompanyName())")
    @Mapping(target = "companyLogo",expression = "java(follow.getJobPost().getCompany().getLogo())")
    @Mapping(target = "companyId",expression = "java(follow.getJobPost().getCompany().getId())")
    @Mapping(target = "jobId",expression = "java(follow.getJobPost().getId())")
    @Mapping(target = "jobTitle",expression = "java(follow.getJobPost().getTitle())")
    @Mapping(target = "jobType",expression = "java(follow.getJobPost().getType())")
    @Mapping(target = "jobVacancies",expression = "java(follow.getJobPost().getVacancies())")
    @Mapping(target = "jobSalaryRange",expression = "java(follow.getJobPost().getSalaryRange())")
    @Mapping(target = "jobAddress",expression = "java(follow.getJobPost().getAddress())")
    @Mapping(target = "jobDeadline",expression = "java(follow.getJobPost().getDeadline())")
    @Mapping(target = "application",ignore = true)
    JobFollowResponse toResponse(JobFollow follow);
}
