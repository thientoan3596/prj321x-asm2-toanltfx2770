package asm02.service;

import asm02.dto.request.update.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import asm02.dto.response.TopCompanyResponse;
import asm02.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CompanyService {
    Long countCompany();
    Page<CompanyResponse> findAll(Pageable pageable);
    List<TopCompanyResponse> findTopCompanies(Integer size);
    CompanyResponse update(CompanyRequest payload);
    List<CompanyResponse> findCompanies(List<String> companiesName);
    List<CompanyResponse> findCompanies(Set<String> companiesName);
    Optional<CompanyResponse> findCompany(long id);
    Optional<CompanyResponse> findCompanyByRecruiter(long recruiterId);

    CompanyResponse uploadLogo(Long companyId, MultipartFile file);
    Optional<Company> getEntity(Long id);
}
