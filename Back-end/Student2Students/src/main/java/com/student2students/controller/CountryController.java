package com.student2students.controller;

import com.student2students.constants.RestParameters;
import com.student2students.dto.CountryDTO;
import com.student2students.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity fetchCountries(@RequestParam(name = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                         @RequestParam(name = RestParameters.LIMIT, required = false, defaultValue = "10") int limit) {
        return countryService.fetchCountries(page, limit);
    }

    @DeleteMapping("/{country}")
    public ResponseEntity deleteCountry(@PathVariable("country") String country) {
        return countryService.deleteCountry(country);
    }
}
