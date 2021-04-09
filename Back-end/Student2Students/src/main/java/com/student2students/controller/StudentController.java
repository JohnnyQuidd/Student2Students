package com.student2students.controller;

import com.student2students.dao.StudentRegisterDAO;
import com.student2students.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(StudentController.class);
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity createNewStudent(@RequestBody StudentRegisterDAO studentDAO) {
        return studentService.registerStudent(studentDAO);
    }

}
