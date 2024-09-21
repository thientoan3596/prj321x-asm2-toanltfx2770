package asm02.service;

import asm02.dto.request.insert.CompanyInsertRequest;
import asm02.dto.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CompanyService {
    CompanyResponse update(CompanyRequest payload);
    CompanyResponse insert(CompanyInsertRequest payload);
    CompanyResponse updateLogo(Long companyId, MultipartFile file);
    Optional<CompanyResponse> findCompany(long id);

}
