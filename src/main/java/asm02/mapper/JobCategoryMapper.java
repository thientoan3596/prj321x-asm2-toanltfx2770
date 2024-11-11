package asm02.mapper;

import asm02.dto.response.JobCategoryResponse;
import asm02.dto.response.TopCategoryResponse;
import asm02.entity.JobCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobCategoryMapper {
    JobCategoryResponse toResponse(JobCategory jobCategory);
//    @Mapping(target = "jobCount",expression = "java(jobCategory.getJobPosts().stream().filter(jp->jp.getDeadline().after()))")
//    @Mapping(target = "jobCount", expression = "java((int) jobCategory.getJobPosts().stream().filter(jp -

//    TopCategoryResponse toTopCategoryResponse(JobCategory jobCategory);
    @Mapping(target = "jobCount", expression = "java(jobCategory.getJobPosts().stream().filter(jp -> jp.getDeadline().after(new java.sql.Date(System.currentTimeMillis()))).count())")
    TopCategoryResponse toTopCategoryResponse(JobCategory jobCategory);

}
