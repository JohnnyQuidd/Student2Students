package com.zuul.controller;

import com.zuul.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/block/{username}")
    public ResponseEntity blockStudent(@PathVariable("username") String username) {
        return adminService.blockStudent(username);
    }

    @PutMapping("/unblock/{username}")
    public ResponseEntity unblockStudent(@PathVariable("username") String username) {
        return adminService.unblockStudent(username);
    }
}
