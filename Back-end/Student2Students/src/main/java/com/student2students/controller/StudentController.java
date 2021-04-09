package com.student2students.controller;

import com.student2students.dao.StudentRegisterDAO;
import com.student2students.model.Student;
import com.student2students.service.StudentRequestService;
import com.student2students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final StudentRequestService studentRequestService;

    @Autowired
    public StudentController(StudentService studentService, StudentRequestService studentRequestService) {
        this.studentService = studentService;
        this.studentRequestService = studentRequestService;
    }


    @PostMapping
    public ResponseEntity createNewStudent(@RequestBody StudentRegisterDAO studentDAO) {
        return studentService.registerStudent(studentDAO);
    }

    @GetMapping("/data")
    public ResponseEntity getStudentData(HttpServletRequest request) {
        Student student = studentRequestService.getStudentFromRequest(request);
        return ResponseEntity.ok(student);
    }

}
