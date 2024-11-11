package asm02.service;

import asm02.dto.request.update.CVRequest;
import asm02.dto.response.CVResponse;
import asm02.entity.CV;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CVService {
    Optional<CVResponse> findById(Long id);
    Optional<CV> getEntity(Long id);
    List<CVResponse> findByUserId(Long userId);
    CVResponse insert(CVRequest payload);
    void delete(Long id);

    CVResponse uploadCv(MultipartFile file,Long userId,Boolean isDefault);
    CVResponse uploadCv(MultipartFile file,Long userId,Boolean isDefault,String name);
}
