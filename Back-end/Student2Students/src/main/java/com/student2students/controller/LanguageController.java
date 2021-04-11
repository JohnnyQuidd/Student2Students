package com.student2students.controller;

import com.student2students.dto.LanguageDTO;
import com.student2students.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/language")
public class LanguageController {
    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    public ResponseEntity addNewLanguage(@RequestBody LanguageDTO languageDTO) {
        return languageService.addNewLanguage(languageDTO);
    }

    @DeleteMapping("/{languageCode}")
    public ResponseEntity deleteLanguage(@PathVariable("languageCode") String languageCode) {
        return languageService.deleteLanguage(languageCode);
    }
}
