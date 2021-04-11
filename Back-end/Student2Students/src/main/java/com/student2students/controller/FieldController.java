package com.student2students.controller;

import com.student2students.dto.FieldDTO;
import com.student2students.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/field")
public class FieldController {
    private final FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping
    public ResponseEntity addNewField(@RequestBody FieldDTO fieldDTO) {
        return fieldService.addNewField(fieldDTO);
    }

    @DeleteMapping("/{fieldName}")
    public ResponseEntity deleteField(@PathVariable("fieldName") String fieldName) {
        return fieldService.deleteField(fieldName);
    }
}
