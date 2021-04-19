package com.student2students.service;

import com.student2students.dto.CountryDTO;
import com.student2students.model.Country;
import com.student2students.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public ResponseEntity<List<CountryDTO>> fetchCountries(int page, int limit) {
        if(page == 0 && limit == 0) {
            List<Country> countries = countryRepository.findAll();
            return ResponseEntity.ok(makeDTOsFromCountryModel(countries));
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "country"));
        List<Country> countries = countryRepository.findAll(pageable).getContent();

        return ResponseEntity.ok(makeDTOsFromCountryModel(countries));
    }

    private List<CountryDTO> makeDTOsFromCountryModel(List<Country> countries) {
        return countries.stream()
                .map(country -> CountryDTO.builder()
                        .id(country.getId())
                        .country(country.getCountry())
                        .build())
                .collect(Collectors.toList());
    }
}
