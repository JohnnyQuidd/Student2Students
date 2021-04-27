package com.student2students.postservice.controller;

import com.student2students.postservice.constants.RestParameters;
import com.student2students.postservice.dto.ExchangeDTO;
import com.student2students.postservice.service.ExchangeService;
import com.student2students.postservice.service.StudentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<?> fetchExchanges(@RequestParam(name = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                            @RequestParam(name = RestParameters.LIMIT, required = false, defaultValue = "0") int limit) {
        return exchangeService.fetchExchanges(page, limit);
    }
}
