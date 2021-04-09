package com.student2students.controller;

import com.student2students.dao.StudentRegisterDAO;
import com.student2students.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/no-role")
    public String testingWithoutRole(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        for(Cookie currentCookie : cookies) {
            if(currentCookie.getName().equals("jwt"))
                cookie = currentCookie;
        }
        logger.info("JWT: " + cookie.getValue());
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
