package asm02.dao;

import asm02.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyDao {
    List<Company> findAll();
    List<Company> findByName(String name);
    Optional<Company> findById(Long id);
    void insert(Company company);
    void update(Company company);
     Optional<Company> findByRecruiter(long recruiterId);
}
