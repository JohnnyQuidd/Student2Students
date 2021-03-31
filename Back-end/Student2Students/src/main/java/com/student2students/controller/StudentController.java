package com.student2students.controller;

import com.student2students.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping
    public List<Student> getAllStudents() {
        return null;
    }

    @PostMapping
    public ResponseEntity createNewStudent(@RequestBody Student student) {
        return null;
    }

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return null;
    }
}
