package com.student2students.service;

import com.student2students.dao.AdminRegisterDAO;
import com.student2students.model.Address;
import com.student2students.model.Admin;
import com.student2students.model.Country;
import com.student2students.model.Language;
import com.student2students.repository.AdminRepository;
import com.student2students.repository.CountryRepository;
import com.student2students.repository.LanguageRepository;
import com.student2students.security.ApplicationUserRole;
import com.student2students.util.UniquenessCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final LanguageRepository languageRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        LanguageRepository languageRepository,
                        CountryRepository countryRepository,
                        PasswordEncoder passwordEncoder,
                        UniquenessCheck uniquenessCheck) {
        this.adminRepository = adminRepository;
        this.languageRepository = languageRepository;
        this.countryRepository = countryRepository;
        this.passwordEncoder = passwordEncoder;
        this.uniquenessCheck = uniquenessCheck;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username);
    }

    public ResponseEntity registerNewAdmin(AdminRegisterDAO adminRegisterDAO) {
        if(!uniquenessCheck.isUsernameUnique(adminRegisterDAO.getUsername())) {
            return ResponseEntity.status(403).body("Username already exists");
        }
        if(!uniquenessCheck.isEmailUnique(adminRegisterDAO.getEmail())) {
            return ResponseEntity.status(403).body("Email already exists");
        }

        Admin admin = createAdminFromDAO(adminRegisterDAO);

        try {
            adminRepository.save(admin);
            return ResponseEntity.status(201).body("Admin created!");
        } catch (Exception e) {
            logger.error("Couldn't persist admin");
            e.printStackTrace();

            return ResponseEntity.status(500).body("Admin couldn't be persisted");
        }
    }

    private Admin createAdminFromDAO(AdminRegisterDAO adminRegisterDAO) {
        Language language = languageRepository.findByLanguage(adminRegisterDAO.getLanguage())
                                                .orElseThrow(() -> new IllegalStateException("Language not found"));
        Country country = countryRepository.findByCountry(adminRegisterDAO.getCountry())
                                                .orElseThrow(() -> new IllegalStateException("Country not found"));

        Address address = Address.builder()
                                .country(country)
                                .city(adminRegisterDAO.getCity())
                                .streetName(adminRegisterDAO.getStreetName())
                                .streetNumber(adminRegisterDAO.getStreetNumber())
                                .build();

        Admin admin = Admin.builder()
                .firstName(adminRegisterDAO.getFirstName())
                .lastName(adminRegisterDAO.getLastName())
                .country(country)
                .address(address)
                .email(adminRegisterDAO.getEmail())
                .username(adminRegisterDAO.getUsername())
                .password(passwordEncoder.encode(adminRegisterDAO.getPassword()))
                .createdAt(LocalDate.now())
                .language(language)
                .userRole(ApplicationUserRole.ADMIN)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .biography(adminRegisterDAO.getBiography())
                .build();

        return admin;
    }


}
