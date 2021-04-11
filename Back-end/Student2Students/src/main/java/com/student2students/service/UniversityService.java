package com.student2students.service;

import com.student2students.dto.UniversityDTO;
import com.student2students.model.Address;
import com.student2students.model.Country;
import com.student2students.model.University;
import com.student2students.repository.CountryRepository;
import com.student2students.repository.UniversityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UniversityService {
    private final UniversityRepository universityRepository;
    private final CountryRepository countryRepository;
    private Logger logger = LoggerFactory.getLogger(UniversityService.class);

    @Autowired
    public UniversityService(UniversityRepository universityRepository,
                             CountryRepository countryRepository) {
        this.universityRepository = universityRepository;
        this.countryRepository = countryRepository;
    }

    @Transactional
    public ResponseEntity addNewUniversity(UniversityDTO universityDTO) {
        if(universityRepository.existsByUniversityName(universityDTO.getUniversityName()))
            return ResponseEntity.status(403).body("University with provided name already exists");

        if(!countryRepository.existsByCountry(universityDTO.getCountry()))
            return ResponseEntity.status(403).body("Provided country is not supported yet!");

        Address address = extractAddressFromDTO(universityDTO);

        University university = University.builder()
                .universityName(universityDTO.getUniversityName())
                .universityEmail(universityDTO.getUniversityEmail())
                .universityAddress(address)
                .build();

        try {
            universityRepository.save(university);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("Couldn't persist university");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private Address extractAddressFromDTO(UniversityDTO universityDTO) {
        Country country = countryRepository.findByCountry(universityDTO.getCountry())
                .orElseThrow(() -> new IllegalStateException("Country not found"));

        return Address.builder()
                .country(country)
                .city(universityDTO.getCity())
                .streetName(universityDTO.getStreetName())
                .streetNumber(universityDTO.getStreetNumber())
                .build();
    }

    @Transactional
    public ResponseEntity deleteUniversity(String universityName) {
        if(!universityRepository.existsByUniversityName(universityName))
            return ResponseEntity.status(403).body("Invalid university name");

        try {
            universityRepository.deleteByUniversityName(universityName);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            logger.error("An error occurred while deleting university");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
