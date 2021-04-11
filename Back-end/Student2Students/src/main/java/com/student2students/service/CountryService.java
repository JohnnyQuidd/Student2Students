package com.student2students.service;

import com.student2students.dto.CountryDTO;
import com.student2students.model.Country;
import com.student2students.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private Logger logger = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional
    public ResponseEntity createNewCountry(CountryDTO countryDTO) {
        if(!countryRepository.existsByCountry(countryDTO.getCountry())) {
            Country country = Country.builder()
                                .country(countryDTO.getCountry())
                                .build();

            try {
                countryRepository.save(country);
                return ResponseEntity.status(201).build();
            } catch (Exception e) {
                logger.error("Couldn't persist country");
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }

        }
        return ResponseEntity.status(403).body("Country with provided name already exists");
    }

    @Transactional
    public ResponseEntity deleteCountry(String country) {
        if(!countryRepository.existsByCountry(country)) {
            return ResponseEntity.status(403).body("Country " + country + " doesn't exist!");
        }

        try {
            countryRepository.deleteByCountry(country);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            logger.error("Couldn't delete country");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
