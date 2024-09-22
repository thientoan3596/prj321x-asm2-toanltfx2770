package asm02.service;

import asm02.dao.CVDao;
import asm02.dto.request.update.CVRequest;
import asm02.dto.response.CVResponse;
import asm02.entity.CV;
import asm02.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CVServiceImpl implements CVService{
    @Autowired
    private CVDao cvDao;
    @Autowired
    private  UserService userService;
    @Autowired
    private FileService fileService;

    @Override
    public Optional<CVResponse> findById(Long id) {
        return cvDao.findById(id).map(CV::toResponse);
    }

    @Override
    public List<CVResponse> findByUserId(Long userId) {
        return cvDao.findByUserId(userId).stream().map(CV::toResponse).collect(Collectors.toList());
    }
    @Override
    public CVResponse insert(CVRequest payload) {
        User user = userService.findUserEntity(payload.getUserId()).orElseThrow(()-> new EntityNotFoundException("No such user with id: " + payload.getUserId()));
        CV cv = payload.toEntity();
        cv.setUser(user);
        cvDao.insert(cv);
        return cv.toResponse();
    }

    @Override
    public void delete(Long id) {
        CV cv = cvDao.findById(id).orElseThrow(()-> new EntityNotFoundException("No such CV with id: " + id));
        cvDao.delete(cv);
    }
    @Override
    public CVResponse uploadCv(MultipartFile file, Long userId,Boolean isDefault) {
        User user = userService.findUserEntity(userId).orElseThrow(()-> new EntityNotFoundException("No such user with id: " + userId));
        String svFileName =fileService.uploadFile(file);
        CV cv = CV.builder()
                .isDefault(true)
                .fileName(file.getOriginalFilename())
                .fileNameOnServer(svFileName)
                .user(user)
                .isDefault(isDefault)
                .build();
        if(isDefault)
            cvDao.updateUserCVsToUnDefault(userId);
        cvDao.insert(cv);
        return cv.toResponse();
    }
}
