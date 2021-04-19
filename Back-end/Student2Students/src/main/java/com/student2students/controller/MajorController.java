package com.student2students.controller;

import com.student2students.constants.RestParameters;
import com.student2students.dto.MajorDTO;
import com.student2students.dto.MajorListDTO;
import com.student2students.model.Major;
import com.student2students.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<?> getMajors(@RequestParam(name = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                       @RequestParam(name = RestParameters.LIMIT, required = false, defaultValue = "0") int limit) {
        //String uri = RestParameters.POSTING_SERVICE_URL + "/major";
        //MajorListDTO majorListDTO = restTemplate.getForObject(uri, MajorListDTO.class);
        return majorService.fetchMajors(page, limit);
    }

    @PostMapping
    public ResponseEntity addNewMajor(@RequestBody MajorDTO majorDTO) {
        //String uri = RestParameters.POSTING_SERVICE_URL + "/major";
        try {
            //MajorDTO responseDTO = restTemplate.postForObject(uri, majorDTO, MajorDTO.class);
            return majorService.addNewMajor(majorDTO);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(403).body("Major " + majorDTO.getMajorName() + " already exists in posting-service");
        }

    }

    @DeleteMapping("/{majorName}")
    public ResponseEntity deleteField(@PathVariable("majorName") String majorName) {
        return majorService.deleteMajor(majorName);
    }
}
