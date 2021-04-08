package com.student2students.controller;

import com.student2students.dao.StudentRegisterDAO;
import com.student2students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/no-role")
    public String testingWithoutRole() {
        return "Free of permissions";
    }

    @GetMapping("/student-role")
    @PreAuthorize("hasAuthority('STUDENT')")
    public String testingForRoleStundet() {
        return "STUDENT";
    }
    @GetMapping("/admin-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String testingForRoleAdmin() {
        return "Admin";
    }

    @PostMapping
    public ResponseEntity createNewStudent(@RequestBody StudentRegisterDAO studentDAO) {
        return studentService.registerStudent(studentDAO);
    }

}
