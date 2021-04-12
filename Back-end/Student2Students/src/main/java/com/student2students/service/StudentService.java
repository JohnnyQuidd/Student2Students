package com.student2students.service;

import com.student2students.dto.StudentDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public ResponseEntity<?> registerStudent(StudentRegisterDTO studentDAO) {
        if(!uniquenessCheck.isUsernameUnique(studentDAO.getUsername())) {
            return ResponseEntity.status(403).body("Username already exists");
        }
        if(!uniquenessCheck.isEmailUnique(studentDAO.getEmail())) {
            return ResponseEntity.status(403).body("Email already exists");
        }

        Student student = createStudentFromDTO(studentDAO);

        try {
            studentRepository.save(student);
        } catch(Exception e) {
            logger.error("Couldn't persist student");
            e.printStackTrace();
        }

        return ResponseEntity.status(201).body("Student registered");
    }

    private Student createStudentFromDTO(StudentRegisterDTO studentDTO) {
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


        return Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
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
    }

    public ResponseEntity<List<StudentDTO>> getStudentsFromCountry(String countryName, int page, int limit) {
        Pageable pageableElement = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "username"));
        List<Student> students = studentRepository.findByAddress_Country_Country(countryName, pageableElement).getContent();
        List<StudentDTO> studentDTOs = makeStudentDTOFromStudentList(students);

        return ResponseEntity.ok(studentDTOs);
    }

    public ResponseEntity<List<StudentDTO>> getStudentsByMajorName(String majorName, int page, int limit) {
        Pageable pageableElement = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "username"));
        List<Student> students = studentRepository.findByMajor_MajorName(majorName, pageableElement).getContent();
        List<StudentDTO> studentDTOS = makeStudentDTOFromStudentList(students);

        return ResponseEntity.ok(studentDTOS);
    }

    public ResponseEntity<List<StudentDTO>> getStudentsByLanguage(String language, int page, int limit) {
        Pageable pageableElement = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "username"));
        List<Student> students = studentRepository.findByLanguage_LanguageName(language, pageableElement).getContent();
        List<StudentDTO> studentDTOS = makeStudentDTOFromStudentList(students);

        return ResponseEntity.ok(studentDTOS);
    }

    private List<StudentDTO> makeStudentDTOFromStudentList(List<Student> students) {
        return students.stream()
                .map(student -> StudentDTO.builder()
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .country(student.getAddress().getCountry().getCountry())
                        .streetName(student.getAddress().getStreetName())
                        .streetNumber(student.getAddress().getStreetNumber())
                        .email(student.getEmail())
                        .username(student.getUsername())
                        .language(student.getLanguage().getLanguageName())
                        .biography(student.getBiography())
                        .majorName(student.getMajor().getMajorName())
                        .build())
                .collect(Collectors.toList());
    }
}
