package com.zuul.repository;

import com.zuul.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int countStudents();
}
