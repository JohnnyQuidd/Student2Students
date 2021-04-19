package com.student2students.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
public class AuthorizationCheck {

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity checkForAdmin() {
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/student")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity checkForStudent() {
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        return ResponseEntity.ok().build();
    }
}
