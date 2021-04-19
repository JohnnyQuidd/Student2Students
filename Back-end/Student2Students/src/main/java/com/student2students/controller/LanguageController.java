package com.student2students.controller;

import com.student2students.constants.RestParameters;
import com.student2students.dto.LanguageDTO;
import com.student2students.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/language")
public class LanguageController {
    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity getLanguages(@RequestParam(value = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                       @RequestParam(value = RestParameters.LIMIT, required = false, defaultValue = "10") int limit) {
        return languageService.fetchLanguages(page, limit);
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
