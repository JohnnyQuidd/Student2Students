package com.student2students.repository;

import com.student2students.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    boolean existsByUniversityName(String universityName);
    long deleteByUniversityName(String universityName);
}
