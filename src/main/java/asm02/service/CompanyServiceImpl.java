package asm02.service;

import asm02.dao.CompanyDao;
import asm02.dto.request.update.CompanyRequest;
import asm02.dto.response.CompanyResponse;
import asm02.entity.Company;
import asm02.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
    public CompanyResponse update(CompanyRequest payload) {
        Company comp = companyDao.findById(payload.getId()).orElseThrow(() -> new EntityNotFoundException("No such company with id: " + payload.getId()));
        comp.merge(payload);
        companyDao.update(comp);
        return companyMapper.toResponse(comp);
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
}
