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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public ResponseEntity fetchAllUniversities() {
        List<University> universities = universityRepository.findAll();

        return ResponseEntity.ok(universities);
    }

    public ResponseEntity fetchParametrized(int page, int limit) {
        if(page == 0 && limit == 0) {
            List<University> universities = universityRepository.findAll();
            return ResponseEntity.ok(universities);
        }
        Pageable pageableElement = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "universityName"));
        Page<University> pageResult = universityRepository.findAll(pageableElement);

        return ResponseEntity.ok(pageResult.getContent());
    }

    public ResponseEntity fetchByCountryName(String countryName) {
        if(!countryRepository.existsByCountry(countryName))
            return ResponseEntity.status(404).body("Country name not found!");

        List<University> universities = universityRepository.findByUniversityAddress_Country_Country(countryName);
        universities = universities.stream()
                .sorted(Comparator.comparing(University::getUniversityName))
                .collect(Collectors.toList());
        return ResponseEntity.ok(universities);
    }

    @Transactional
    public ResponseEntity<?> updateUniversityData(UniversityDTO universityDTO) {
        if(!isUpdateDataValid(universityDTO)) {
            return ResponseEntity.status(403).body("Invalid Update data");
        }

        Country country = countryRepository.findByCountry(universityDTO.getCountry())
                    .orElseThrow(() -> new IllegalStateException("Country is not supported yet!"));

        University university = universityRepository.findById(universityDTO.getId())
                    .orElseThrow(() -> new IllegalStateException("University not found!"));

        Address address = Address.builder()
                .city(universityDTO.getCity())
                .country(country)
                .streetNumber(universityDTO.getStreetNumber())
                .streetName(universityDTO.getStreetName())
                .build();

        university.setUniversityName(universityDTO.getUniversityName());
        university.setUniversityEmail(universityDTO.getUniversityEmail());
        university.setUniversityAddress(address);

        try {
            universityRepository.save(university);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("An error occurred while updating university data");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private boolean isUpdateDataValid(UniversityDTO dto) {
        return !dto.getUniversityName().equals("")
                && !dto.getUniversityEmail().equals("")
                && !dto.getCountry().equals("")
                && !dto.getCity().equals("")
                && !dto.getStreetName().equals("")
                && !dto.getStreetNumber().equals("");
    }
}
