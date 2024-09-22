package asm02.mapper;

import asm02.dto.request.insert.CompanyInsertRequest;
import asm02.dto.response.CompanyResponse;
import asm02.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyResponse toResponse(Company company);

    Company toEntity(CompanyResponse payload);
    Company toEntity(CompanyInsertRequest payload);

}
