package com.example.student2students.controller;

import com.example.student2students.dto.EmailDTO;
import com.example.student2students.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> sendActivationEmail(@RequestBody EmailDTO emailDTO) {
        return emailService.sendActivationEmail(emailDTO);
    }

}
