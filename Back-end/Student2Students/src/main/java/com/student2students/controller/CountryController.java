package com.student2students.controller;

import com.student2students.dto.CountryDTO;
import com.student2students.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity createNewCountry(@RequestBody CountryDTO countryDTO) {
        return countryService.createNewCountry(countryDTO);
    }

    @DeleteMapping("/{country}")
    public ResponseEntity deleteCountry(@PathVariable("country") String country) {
        return countryService.deleteCountry(country);
    }
}
