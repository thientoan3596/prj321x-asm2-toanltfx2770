package asm02.service;

import asm02.dao.CompanyDao;
import asm02.dto.request.update.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import asm02.dto.response.TopCompanyResponse;
import asm02.entity.Company;
import asm02.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private FileService fileService;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private CompanyMapper companyMapper;


    @Override
    public Long countCompany(){
        return companyDao.countCompany();
    }

    @Override
    public Page<CompanyResponse> findAll(Pageable pageable) {
        return companyDao.findAll(pageable).map(companyMapper::toResponse);
    }

    @Override
    public List<TopCompanyResponse> findTopCompanies(Integer size){
        return companyDao.findTopCompanies(size).stream().map(companyMapper::toTopCompanyResponse).collect(Collectors.toList());
    }
    @Override
    public CompanyResponse update(CompanyRequest payload) {
        Company comp = companyDao.findById(payload.getId()).orElseThrow(() -> new EntityNotFoundException("No such company with id: " + payload.getId()));
        comp.merge(payload);
        companyDao.update(comp);
        return companyMapper.toResponse(comp);
    }
    @Override
    public List<CompanyResponse> findCompanies(Set<String> companiesName){
        return findCompanies(new ArrayList<>(companiesName));
    }

    @Override
    public List<CompanyResponse> findCompanies(List<String> companiesName){
        return companyDao.findCompaniesByName(companiesName).stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }
    @Override
    public Optional<CompanyResponse> findCompany(long id) {
        return companyDao.findById(id).map(companyMapper::toResponse);
    }

    @Override
    public Optional<CompanyResponse> findCompanyByRecruiter(long recruiterId) {
        return companyDao.findByRecruiter(recruiterId).map(companyMapper::toResponse);
    }

    @Override
    public CompanyResponse uploadLogo(Long companyId, MultipartFile file) {
        Company comp = companyDao.findById(companyId).orElseThrow(() -> new EntityNotFoundException("No such company with id: " + companyId));
        String oldLogo = comp.getLogo();
        String newLogo = fileService.uploadFile(file);
        comp.setLogo(newLogo);
        companyDao.update(comp);
        if (oldLogo != null)
            fileService.deleteFile(oldLogo);
        return companyMapper.toResponse(comp);
    }
    @Override
    public Optional<Company> getEntity(Long id) {
        return companyDao.findById(id);
    }
}
