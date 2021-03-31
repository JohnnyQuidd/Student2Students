package com.student2students.Student2Students.controller;

import com.student2students.Student2Students.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "skinnypete", "Petar", "Kovacevic", "Serbia", "Novi Sad"),
            new Student(2, "admin", "Lazar", "Simic", "Serbia", "Kraljevo"),
            new Student(3, "maja98", "Marija", "Misic", "Serbia", "Beograd"),
            new Student(4, "milica99", "Milica", "Kovacevic", "Serbia", "Kraljevo")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        System.out.println("GET");
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println("Adding a new student " + student.getId() + " " + student.getFirstName() );
        STUDENTS.add(student);
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer id) {
        System.out.println("Deleting a student");
    }

    @PutMapping("/update/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId,
                              @RequestBody Student student) {
        System.out.println(String.format("%s %s", studentId, student));
    }
}
