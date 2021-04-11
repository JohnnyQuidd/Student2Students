package com.student2students.repository;

import com.student2students.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    Optional<Major> findByMajorName(String majorName);
    boolean existsByMajorName(String majorName);
    long deleteByMajorName(String majorName);
}
