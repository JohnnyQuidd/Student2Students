package com.student2students.Student2Students.repository;

import com.student2students.Student2Students.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findStudentById(Integer id);
}
