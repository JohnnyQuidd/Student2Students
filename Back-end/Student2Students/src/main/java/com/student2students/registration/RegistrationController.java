package com.student2students.registration;

import com.student2students.dto.AdminRegisterDTO;
import com.student2students.dto.StudentRegisterDTO;
import com.student2students.service.AdminService;
import com.student2students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
