package asm02.mapper;

import asm02.dto.request.update.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import asm02.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {


    @Mapping(target = "recruiter_id",expression = "java(company.getRecruiter().getId())")
    CompanyResponse toResponse(Company company);


    CompanyRequest toRequest(CompanyResponse response);

    @Mapping(target="createdAt",ignore = true)
    @Mapping(target="deletedAt",ignore = true)
    @Mapping(target="recruiter",ignore = true)
    Company toEntity(CompanyResponse payload);

}
