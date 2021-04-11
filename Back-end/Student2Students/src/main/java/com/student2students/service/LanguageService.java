package com.student2students.service;

import com.student2students.dto.LanguageDTO;
import com.student2students.model.Language;
import com.student2students.repository.LanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;
    private Logger logger = LoggerFactory.getLogger(LanguageService.class);

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Transactional
    public ResponseEntity addNewLanguage(LanguageDTO languageDTO) {
        if(languageRepository.existsByLanguageName(languageDTO.getLanguageName()))
            return ResponseEntity.status(403).body("Language is already in use");
        if(languageRepository.existsByLanguageCode(languageDTO.getLanguageCode()))
            return ResponseEntity.status(403).body("Language code is already in use");

        try {
            Language language = Language.builder()
                    .languageName(languageDTO.getLanguageName())
                    .languageCode(languageDTO.getLanguageCode())
                    .build();
            languageRepository.save(language);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("An error occurred while persisting language " + languageDTO);
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error persisting language");
        }
    }

    @Transactional
    public ResponseEntity deleteLanguage(String languageCode) {
        if(!languageRepository.existsByLanguageCode(languageCode))
            return ResponseEntity.status(403).body("Language code doesn't exist");

        try {
            languageRepository.deleteByLanguageCode(languageCode);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            logger.error("An error occurred while deleting language" + languageCode);
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting language" + languageCode);
        }
    }
}
