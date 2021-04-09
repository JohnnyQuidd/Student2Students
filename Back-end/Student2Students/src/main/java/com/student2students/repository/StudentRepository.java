package com.student2students.repository;

import com.student2students.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
