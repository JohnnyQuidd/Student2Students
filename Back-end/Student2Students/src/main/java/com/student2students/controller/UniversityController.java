package com.student2students.controller;

import com.student2students.constants.RestParameters;
import com.student2students.dto.UniversityDTO;
import com.student2students.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/university")
public class UniversityController {
    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public ResponseEntity fetchAllUniversities() {
        return universityService.fetchAllUniversities();
    }

    @GetMapping("/pagination")
    public ResponseEntity fetchParametrizedUniversities(@RequestParam(value = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = RestParameters.LIMIT, required = false, defaultValue = "10") int limit) {
        return universityService.fetchParametrized(page, limit);
    }

    @GetMapping("/{countryName}")
    public ResponseEntity fetchByCountryName(@PathVariable("countryName") String countryName) {
        return universityService.fetchByCountryName(countryName);
    }

    @PostMapping
    public ResponseEntity addNewUniversity(@RequestBody UniversityDTO universityDTO) {
        return universityService.addNewUniversity(universityDTO);
    }

    @DeleteMapping("/{universityName}")
    public ResponseEntity deleteUniversity(@PathVariable("universityName") String universityName) {
        return universityService.deleteUniversity(universityName);
    }
}
