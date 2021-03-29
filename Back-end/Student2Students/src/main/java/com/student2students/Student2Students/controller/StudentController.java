package com.student2students.Student2Students.controller;

import com.student2students.Student2Students.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Petar Kovacevic"),
            new Student(2, "Lazar Simic"),
            new Student(3, "Marija Misic"),
            new Student(4, "Uros Blagojevic")
    );

    @GetMapping
    public List<Student> getAllStudents() {
        return StudentController.STUDENTS;
    }

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream().filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exist"));

    }
}
