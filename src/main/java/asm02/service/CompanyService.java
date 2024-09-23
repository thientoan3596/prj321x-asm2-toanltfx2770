package asm02.service;

import asm02.dto.request.update.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CompanyService {
    CompanyResponse update(CompanyRequest payload);
    Optional<CompanyResponse> findCompany(long id);
    Optional<CompanyResponse> findCompanyByRecruiter(long recruiterId);

    CompanyResponse uploadLogo(Long companyId, MultipartFile file);
}
