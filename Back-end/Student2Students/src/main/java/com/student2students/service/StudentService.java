package com.student2students.service;

import com.student2students.dto.StudentRegisterDTO;
import com.student2students.model.*;
import com.student2students.repository.CountryRepository;
import com.student2students.repository.LanguageRepository;
import com.student2students.repository.MajorRepository;
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

import java.time.LocalDate;

@Service
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final MajorRepository majorRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          CountryRepository countryRepository,
                          LanguageRepository languageRepository,
                          MajorRepository majorRepository,
                          PasswordEncoder passwordEncoder,
                          UniquenessCheck uniquenessCheck) {
        this.studentRepository = studentRepository;
        this.countryRepository = countryRepository;
        this.languageRepository = languageRepository;
        this.majorRepository = majorRepository;
        this.passwordEncoder = passwordEncoder;
        this.uniquenessCheck = uniquenessCheck;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByUsername(username);
    }

    public ResponseEntity registerStudent(StudentRegisterDTO studentDAO) {
        if(!uniquenessCheck.isUsernameUnique(studentDAO.getUsername())) {
            return ResponseEntity.status(403).body("Username already exists");
        }
        if(!uniquenessCheck.isEmailUnique(studentDAO.getEmail())) {
            return ResponseEntity.status(403).body("Email already exists");
        }

        Student student = createStudentFromDAO(studentDAO);

        try {
            studentRepository.save(student);
        } catch(Exception e) {
            logger.error("Couldn't persist student");
            e.printStackTrace();
        }

        return ResponseEntity.status(201).body("Student registered");
    }

    private Student createStudentFromDAO(StudentRegisterDTO studentDTO) {
        Country country = countryRepository.findByCountry(studentDTO.getCountry())
                                .orElseThrow(() -> new IllegalStateException("Country not found!"));
        Language language = languageRepository.findByLanguageName(studentDTO.getLanguage())
                                .orElseThrow(() -> new IllegalStateException("Language not found!"));

        Address address = Address.builder()
                            .country(country)
                            .city(studentDTO.getCity())
                            .streetName(studentDTO.getStreetName())
                            .streetNumber(studentDTO.getStreetNumber())
                            .build();
        Major major = majorRepository.findByMajorName(studentDTO.getMajorName())
                .orElseThrow(() -> new IllegalStateException("Major cannot be found"));


        Student student = Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .country(country)
                .address(address)
                .email(studentDTO.getEmail())
                .username(studentDTO.getUsername())
                .password(passwordEncoder.encode(studentDTO.getPassword()))
                .language(language)
                .userRole(ApplicationUserRole.STUDENT)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .createdAt(LocalDate.now())
                .biography(studentDTO.getBiography())
                .major(major)
                .build();

        return student;
    }
}
