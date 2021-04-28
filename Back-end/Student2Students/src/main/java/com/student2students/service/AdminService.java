package com.student2students.service;

import com.student2students.dto.AdminRegisterDTO;
import com.student2students.model.*;
import com.student2students.repository.AdminRepository;
import com.student2students.repository.CountryRepository;
import com.student2students.repository.LanguageRepository;
import com.student2students.repository.StudentRepository;
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

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class AdminService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final LanguageRepository languageRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        StudentRepository studentRepository,
                        LanguageRepository languageRepository,
                        CountryRepository countryRepository,
                        PasswordEncoder passwordEncoder,
                        UniquenessCheck uniquenessCheck) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.languageRepository = languageRepository;
        this.countryRepository = countryRepository;
        this.passwordEncoder = passwordEncoder;
        this.uniquenessCheck = uniquenessCheck;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username);
    }

    @Transactional
    public ResponseEntity registerNewAdmin(AdminRegisterDTO adminRegisterDTO) {
        if(!uniquenessCheck.isUsernameUnique(adminRegisterDTO.getUsername())) {
            return ResponseEntity.status(403).body("Username already exists");
        }
        if(!uniquenessCheck.isEmailUnique(adminRegisterDTO.getEmail())) {
            return ResponseEntity.status(403).body("Email already exists");
        }

        Admin admin = createAdminFromDAO(adminRegisterDTO);

        try {
            adminRepository.save(admin);
            return ResponseEntity.status(201).body("Admin created!");
        } catch (Exception e) {
            logger.error("Couldn't persist admin");
            e.printStackTrace();

            return ResponseEntity.status(500).body("Admin couldn't be persisted");
        }
    }

    private Admin createAdminFromDAO(AdminRegisterDTO adminRegisterDTO) {
        Language language = languageRepository.findByLanguageName(adminRegisterDTO.getLanguage())
                                                .orElseThrow(() -> new IllegalStateException("Language not found"));
        Country country = countryRepository.findByCountry(adminRegisterDTO.getCountry())
                                                .orElseThrow(() -> new IllegalStateException("Country not found"));

        Address address = Address.builder()
                                .country(country)
                                .city(adminRegisterDTO.getCity())
                                .streetName(adminRegisterDTO.getStreetName())
                                .streetNumber(adminRegisterDTO.getStreetNumber())
                                .build();

        Admin admin = Admin.builder()
                .firstName(adminRegisterDTO.getFirstName())
                .lastName(adminRegisterDTO.getLastName())
                .address(address)
                .email(adminRegisterDTO.getEmail())
                .username(adminRegisterDTO.getUsername())
                .password(passwordEncoder.encode(adminRegisterDTO.getPassword()))
                .createdAt(LocalDate.now())
                .language(language)
                .userRole(ApplicationUserRole.ADMIN)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .biography(adminRegisterDTO.getBiography())
                .build();

        return admin;
    }


    @Transactional
    public ResponseEntity blockStudent(String username) {
        if(!studentRepository.existsByUsername(username))
            return ResponseEntity.status(403).body("Username is not valid");

        Student student = studentRepository.findByUsername(username);

        try {
            student.setAccountNonLocked(false);
            studentRepository.save(student);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            logger.debug("Couldn't block student " + username);
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @Transactional
    public ResponseEntity unblockStudent(String username) {
        if(!studentRepository.existsByUsername(username))
            return ResponseEntity.status(403).body("Username is not valid");

        Student student = studentRepository.findByUsername(username);

        try {
            student.setAccountNonLocked(true);
            studentRepository.save(student);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            logger.debug("Couldn't unblock student " + username);
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
