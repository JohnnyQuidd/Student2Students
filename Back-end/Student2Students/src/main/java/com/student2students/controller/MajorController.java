package com.student2students.controller;

import com.student2students.constants.RestParameters;
import com.student2students.dto.MajorDTO;
import com.student2students.dto.MajorListDTO;
import com.student2students.model.Major;
import com.student2students.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@RequestMapping("/major")
public class MajorController {
    private final MajorService majorService;
    private final RestTemplate restTemplate;

    @Autowired
    public MajorController(MajorService majorService, RestTemplate restTemplate) {
        this.majorService = majorService;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<?> getMajors() {
        String uri = RestParameters.POSTING_SERVICE_URL + "/major";
        MajorListDTO majorListDTO = restTemplate.getForObject(uri, MajorListDTO.class);

        return ResponseEntity.ok().body(majorListDTO);
    }

    @PostMapping
    public ResponseEntity addNewMajor(@RequestBody MajorDTO majorDTO) {
        String uri = RestParameters.POSTING_SERVICE_URL + "/major";
        MajorDTO responseDTO = restTemplate.postForObject(uri, majorDTO, MajorDTO.class);
        System.out.println(responseDTO);
        return majorService.addNewMajor(majorDTO);
    }

    @DeleteMapping("/{majorName}")
    public ResponseEntity deleteField(@PathVariable("fieldName") String majorName) {
        return majorService.deleteMajor(majorName);
    }
}
