package com.zuul.repository;

import com.zuul.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Page<Student> findByAddress_Country_Country(String countryName, Pageable pageable);
    Page<Student> findByMajor_MajorName(String majorName, Pageable pageable);
    Page<Student> findByLanguage_LanguageName(String language, Pageable pageable);
}
