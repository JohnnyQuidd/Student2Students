package com.student2students.postservice.controller;

import com.student2students.postservice.dto.ExchangeDTO;
import com.student2students.postservice.service.ExchangeService;
import com.student2students.postservice.service.StudentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    private final ExchangeService exchangeService;
    private final StudentRequestService requestService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService,
                              StudentRequestService requestService) {
        this.exchangeService = exchangeService;
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<?> createNewExchange(@RequestBody ExchangeDTO exchangeDTO, HttpServletRequest request) {
        String username = requestService.getStudentFromRequest(request);
        if(username == null) {
            return ResponseEntity.status(403).body("Permission denied");
        }
        return exchangeService.createNewExchange(exchangeDTO, username);
    }
}
