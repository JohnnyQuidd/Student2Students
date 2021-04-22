package com.zuul.controller;

import com.zuul.dto.AdminRegisterDTO;
import com.zuul.dto.StudentRegisterDTO;
import com.zuul.service.AdminService;
import com.zuul.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/registration")
public class RegistrationController {
    private final StudentService studentService;
    private final AdminService adminService;

    @Autowired
    public RegistrationController(StudentService studentService, AdminService adminService) {
        this.studentService = studentService;
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<?> activateStudent(@RequestParam("token") String token) {
        return studentService.activateStudent(token);
    }

    @PostMapping("/student")
    public ResponseEntity registerUser(@RequestBody StudentRegisterDTO studentDAO) {
        return studentService.registerStudent(studentDAO);
    }

    @PostMapping("/admin")
    public ResponseEntity registerAdmin(@RequestBody AdminRegisterDTO adminRegisterDTO) {
        return adminService.registerNewAdmin(adminRegisterDTO);
    }
}
