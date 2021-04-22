package com.student2students.service;

import com.student2students.dto.StudentDTO;
import com.student2students.dto.StudentRegisterDTO;
import com.student2students.model.Address;
import com.student2students.model.Country;
import com.student2students.model.Student;
import com.student2students.repository.CountryRepository;
import com.student2students.repository.StudentRepository;
import com.student2students.security.ApplicationUserRole;
import com.student2students.util.UniquenessCheck;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final String HOST_ADDRESS = "http://localhost:8080";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByUsername(username);
    }

    @Transactional
    public ResponseEntity<?> registerStudent(StudentRegisterDTO studentDTO) {
        if(!uniquenessCheck.isUsernameUnique(studentDTO.getUsername())) {
            return ResponseEntity.status(403).body("Username already exists");
        }
        if(!uniquenessCheck.isEmailUnique(studentDTO.getEmail())) {
            return ResponseEntity.status(403).body("Email already exists");
        }

        Student student = createStudentFromDTO(studentDTO);

        try {
            studentRepository.save(student);
        } catch(Exception e) {
            logger.error("Couldn't persist student");
            e.printStackTrace();
            return ResponseEntity.status(500).body("Couldn't persist student");
        }

        return ResponseEntity.status(201).body("Student registered");
    }

    private Student createStudentFromDTO(StudentRegisterDTO studentDTO) {
        Country country = countryRepository.findByCountry(studentDTO.getCountry())
                                .orElseThrow(() -> new IllegalStateException("Country not found!"));

        Address address = Address.builder()
                            .country(country)
                            .city(studentDTO.getCity())
                            .build();


        return Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .address(address)
                .email(studentDTO.getEmail())
                .username(studentDTO.getUsername())
                .password(passwordEncoder.encode(studentDTO.getPassword()))
                .userRole(ApplicationUserRole.STUDENT)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .createdAt(LocalDate.now())
                .biography(studentDTO.getBiography())
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
