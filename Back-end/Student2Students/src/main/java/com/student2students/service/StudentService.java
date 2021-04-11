package com.student2students.service;

import com.student2students.dao.StudentRegisterDAO;
import com.student2students.model.Address;
import com.student2students.model.Country;
import com.student2students.model.Language;
import com.student2students.model.Student;
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

import java.time.LocalDate;

@Service
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          CountryRepository countryRepository,
                          LanguageRepository languageRepository,
                          PasswordEncoder passwordEncoder,
                          UniquenessCheck uniquenessCheck) {
        this.studentRepository = studentRepository;
        this.countryRepository = countryRepository;
        this.languageRepository = languageRepository;
        this.passwordEncoder = passwordEncoder;
        this.uniquenessCheck = uniquenessCheck;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByUsername(username);
    }

    public ResponseEntity registerStudent(StudentRegisterDAO studentDAO) {
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

    private Student createStudentFromDAO(StudentRegisterDAO studentDAO) {
        Country country = countryRepository.findByCountry(studentDAO.getCountry())
                                .orElseThrow(() -> new IllegalStateException("Country not found!"));
        Language language = languageRepository.findByLanguage(studentDAO.getLanguage())
                                .orElseThrow(() -> new IllegalStateException("Language not found!"));

        Address address = Address.builder()
                            .country(country)
                            .city(studentDAO.getCity())
                            .streetName(studentDAO.getStreetName())
                            .streetNumber(studentDAO.getStreetNumber())
                            .build();


        Student student = Student.builder()
                .firstName(studentDAO.getFirstName())
                .lastName(studentDAO.getLastName())
                .country(country)
                .address(address)
                .email(studentDAO.getEmail())
                .username(studentDAO.getUsername())
                .password(passwordEncoder.encode(studentDAO.getPassword()))
                .language(language)
                .userRole(ApplicationUserRole.STUDENT)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .createdAt(LocalDate.now())
                .biography(studentDAO.getBiography())
                .build();

        return student;
    }
}
