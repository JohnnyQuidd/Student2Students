package com.student2students.service;

import com.student2students.dto.FieldDTO;
import com.student2students.model.Field;
import com.student2students.repository.FieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FieldService {
    private final FieldRepository fieldRepository;
    private Logger logger = LoggerFactory.getLogger(FieldService.class);

    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Transactional
    public ResponseEntity addNewField(FieldDTO fieldDTO) {
        Field field = Field.builder()
                .fieldName(fieldDTO.getFieldName())
                .build();
        if(!fieldRepository.existsByFieldName(field.getFieldName())){
            try {
                fieldRepository.save(field);
                return ResponseEntity.status(201).build();
            } catch (Exception e) {
                logger.error("An error while persisting field!");
                e.printStackTrace();
                return ResponseEntity.status(500).body("Couldn't persist field");
            }
        }
        return ResponseEntity.status(403).body("Field " + field.getFieldName() + " already exists");
    }

    @Transactional
    public ResponseEntity deleteField(String fieldName) {
        if(!fieldRepository.existsByFieldName(fieldName)) {
            return ResponseEntity.status(403).body("Field " + fieldName + " doesn't exist!");
        }

        try {
            fieldRepository.deleteByFieldName(fieldName);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            logger.error("Couldn't delete field");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
