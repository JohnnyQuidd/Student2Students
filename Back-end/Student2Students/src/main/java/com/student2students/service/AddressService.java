package com.student2students.service;

import com.student2students.dto.AddressDTO;
import com.student2students.model.Address;
import com.student2students.model.Country;
import com.student2students.repository.AddressRepository;
import com.student2students.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AddressService {
    private final CountryRepository countryRepository;
    private Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    public AddressService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Transactional
    public ResponseEntity createNewAddress(AddressDTO addressDTO) {
        Country country = countryRepository.findByCountry(addressDTO.getCountry())
                .orElseThrow(() -> new IllegalStateException("Country cannot be found!"));

        Address address = Address.builder()
                .country(country)
                .city(addressDTO.getCity())
                .streetName(addressDTO.getStreetName())
                .streetNumber(addressDTO.getStreetNumber())
                .build();

        country.getAddresses().add(address);

        try {
            countryRepository.save(country);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("Couldn't update Country with modified address!");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

    }
}
