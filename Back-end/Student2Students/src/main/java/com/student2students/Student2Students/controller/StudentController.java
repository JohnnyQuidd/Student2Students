package com.student2students.Student2Students.controller;

import com.student2students.Student2Students.model.Student;
import com.student2students.Student2Students.service.StudentRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentRepositoryService studentService;

    @Autowired
    public StudentController(StudentRepositoryService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity createNewStudent(@RequestBody Student student) {
        if(studentService.createNewStudent(student))
            return ResponseEntity.status(201).build();
        return ResponseEntity.status(500).build();
    }

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return studentService.findStudentById(studentId);

    }
}
