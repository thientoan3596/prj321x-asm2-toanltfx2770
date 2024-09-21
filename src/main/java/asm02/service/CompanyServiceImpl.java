package asm02.service;

import asm02.dao.CompanyDao;
import asm02.dto.request.insert.CompanyInsertRequest;
import asm02.dto.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import asm02.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService{
    @Autowired
    private FileService fileService;
    @Autowired
    private CompanyDao companyDao;
    @Override
    public CompanyResponse update(CompanyRequest payload) {
        Company comp = companyDao.findById(payload.getId()).orElseThrow(()-> new EntityNotFoundException("No such company with id: " + payload.getId()));
        comp.merge(payload);
        companyDao.update(comp);
        return comp.toResponse();
    }

    @Override
    public CompanyResponse insert(CompanyInsertRequest payload) {
        Company comp = payload.toEntity();
        companyDao.insert(comp);
        return comp.toResponse();
    }

    @Override
    public CompanyResponse updateLogo(Long companyId, MultipartFile file) {
        Company comp = companyDao.findById(companyId).orElseThrow(()-> new EntityNotFoundException("No such company with id: " + companyId));
        String oldLogo = comp.getLogo();
        String newLogo = fileService.uploadFile(file);
        comp.setLogo(newLogo);
        fileService.deleteFile(oldLogo);
        companyDao.update(comp);
        return comp.toResponse();
    }

    @Override
    public Optional<CompanyResponse> findCompany(long id) {
        return companyDao.findById(id).map(Company::toResponse);
    }
}
