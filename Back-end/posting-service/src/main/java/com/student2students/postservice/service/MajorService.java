package com.student2students.postservice.service;

import com.student2students.postservice.dto.MajorDTO;
import com.student2students.postservice.model.Major;
import com.student2students.postservice.repository.MajorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MajorService {
    private final MajorRepository majorRepository;
    private Logger logger = LoggerFactory.getLogger(MajorService.class);

    @Autowired
    public MajorService(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }

    @Transactional
    public ResponseEntity addNewMajor(MajorDTO majorDTO) {
        Major major = Major.builder()
                .majorName(majorDTO.getMajorName())
                .build();
        if(!majorRepository.existsByMajorName(major.getMajorName())){
            try {
                majorRepository.save(major);
                return ResponseEntity.status(201).build();
            } catch (Exception e) {
                logger.error("An error while persisting major!");
                e.printStackTrace();
                return ResponseEntity.status(500).body("Couldn't persist major");
            }
        }
        return ResponseEntity.status(403).body("Major " + major.getMajorName() + " already exists");
    }

    @Transactional
    public ResponseEntity deleteMajor(String majorName) {
        if(!majorRepository.existsByMajorName(majorName)) {
            return ResponseEntity.status(403).body("Major " + majorName + " doesn't exist!");
        }

        try {
            majorRepository.deleteByMajorName(majorName);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            logger.error("Couldn't delete major");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
