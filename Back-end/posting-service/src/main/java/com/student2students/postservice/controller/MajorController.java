package com.student2students.postservice.controller;

import com.student2students.postservice.dto.MajorDTO;
import com.student2students.postservice.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/major")
public class MajorController {
    private final MajorService majorService;

    @Autowired
    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @PostMapping
    public ResponseEntity addNewField(@RequestBody MajorDTO majorDTO) {
        return majorService.addNewMajor(majorDTO);
    }

    @DeleteMapping("/{majorName}")
    public ResponseEntity deleteField(@PathVariable("fieldName") String majorName) {
        return majorService.deleteMajor(majorName);
    }
}
