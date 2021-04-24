package com.student2students.postservice.controller;

import com.student2students.postservice.constants.RestParameters;
import com.student2students.postservice.dto.MajorDTO;
import com.student2students.postservice.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/major")
public class MajorController {
    private final MajorService majorService;

    @Autowired
    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping
    public ResponseEntity<?> fetchMajors(@RequestParam(value = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                         @RequestParam(value = RestParameters.LIMIT, required = false, defaultValue = "10") int limit) {
        return majorService.fetchMajors(page, limit);
    }

    @PostMapping
    public ResponseEntity<?> addNewMajor(@RequestBody MajorDTO majorDTO) {
        return majorService.addNewMajor(majorDTO);
    }

    @DeleteMapping("/{majorName}")
    public ResponseEntity<?> deleteField(@PathVariable("fieldName") String majorName) {
        return majorService.deleteMajor(majorName);
    }
}
