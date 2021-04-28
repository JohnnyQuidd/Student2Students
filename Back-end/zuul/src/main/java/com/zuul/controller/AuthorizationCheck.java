package com.zuul.controller;

import com.zuul.service.StudentService;
import com.zuul.util.Authorization;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/authorization")
public class AuthorizationCheck {
    private final Authorization authorization;
    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(AuthorizationCheck.class);

    @Autowired
    public AuthorizationCheck(Authorization authorization,
                              StudentService studentService) {
        this.authorization = authorization;
        this.studentService = studentService;
    }

    @GetMapping("/username")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> getUsernameOfLoggedInUser(HttpServletRequest request) {
        String username = authorization.getStudentFromRequest(request);
        return ResponseEntity.ok(username);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity checkForAdmin() {
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/student")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity checkForStudent(HttpServletRequest request) {
        String username = authorization.getStudentFromRequest(request);
        logger.info(username);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/email")
    public ResponseEntity<?> fetchEmailForUser(HttpServletRequest request) {
        String username = authorization.getStudentFromRequest(request);
        if(username == null)
            return ResponseEntity.status(403).build();
        return studentService.fetchEmailForStudent(username);
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        return ResponseEntity.ok().build();
    }
}
