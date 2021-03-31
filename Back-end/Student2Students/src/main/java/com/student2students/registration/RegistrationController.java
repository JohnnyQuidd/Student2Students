package com.student2students.registration;

import com.student2students.dao.StudentRegisterDAO;
import com.student2students.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/registration")
public class RegistrationController {
    private RegistrationService registrationService;
    private StudentService studentService;

    public RegistrationController(RegistrationService registrationService, StudentService studentService) {
        this.registrationService = registrationService;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity register(@RequestBody StudentRegisterDAO studentDAO) {
        return studentService.registerStudent(studentDAO);
    }
}
