package asm02.dao;

import asm02.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyDao {
    Long countCompany();
    List<Company> findAll();
    List<Company> findTopCompanies(Integer size);

    List<Company> findCompaniesByName(List<String> names);
    List<Company> findByName(String name);
    Optional<Company> findById(Long id);
    void insert(Company company);
    void update(Company company);
     Optional<Company> findByRecruiter(long recruiterId);

    Page<Company> findAll(Pageable pageable);
}
