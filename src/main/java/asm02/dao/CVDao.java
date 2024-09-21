package asm02.dao;

import asm02.entity.CV;

import java.util.List;
import java.util.Optional;

public interface CVDao {

    void insert(CV cv);
    void delete(CV cv);
    void updateUserCVsToUnDefault(Long userId);
    List<CV> findByUserId(Long userId);
    Optional<CV> findById(Long id);
}
