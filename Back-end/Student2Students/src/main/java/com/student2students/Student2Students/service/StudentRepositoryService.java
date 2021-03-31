package com.student2students.Student2Students.service;

import com.student2students.Student2Students.model.Student;
import com.student2students.Student2Students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentRepositoryService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentRepositoryService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student findStudentById(Integer studentId) {
        return studentRepository.findStudentById(studentId);
    }

    public boolean createNewStudent(Student student) {
        try {
            studentRepository.save(student);
            return true;
        } catch (Exception e) {
            return  false;
        }
    }
}
