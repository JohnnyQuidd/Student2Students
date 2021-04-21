package com.student2students.service;

import com.google.gson.Gson;
import com.student2students.dto.EmailDTO;
import com.student2students.dto.StudentDTO;
import com.student2students.dto.StudentRegisterDTO;
import com.student2students.message_broker.MessagePublisher;
import com.student2students.model.*;
import com.student2students.registration.RegistrationToken;
import com.student2students.repository.*;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final MajorRepository majorRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final RegistrationTokenRepository tokenRepository;
    private final MessagePublisher messagePublisher;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final String HOST_ADDRESS = "http://localhost:8080";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByUsername(username);
    }

    @Transactional
    public ResponseEntity<?> registerStudent(StudentRegisterDTO studentDAO) {
        if(!uniquenessCheck.isUsernameUnique(studentDAO.getUsername())) {
            return ResponseEntity.status(403).body("Username already exists");
        }
        if(!uniquenessCheck.isEmailUnique(studentDAO.getEmail())) {
            return ResponseEntity.status(403).body("Email already exists");
        }

        Student student = createStudentFromDTO(studentDAO);
        RegistrationToken token = RegistrationToken.builder()
                    .token(UUID.randomUUID().toString())
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(15))
                    .username(student.getUsername())
                    .confirmed(false)
                    .build();
        EmailDTO emailDTO = EmailDTO.builder()
                .activationLink(HOST_ADDRESS + "/manage/registration?token=" + token.getToken())
                .subject("Account activation")
                .receiverEmail(student.getEmail())
                .receiverFirstName(student.getFirstName())
                .content("content")
                .build();

        Gson gson = new Gson();
        String emailGson = gson.toJson(emailDTO);
        try {
            tokenRepository.save(token);
            studentRepository.save(student);
            messagePublisher.sendEmailToTheQueue(emailGson);
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
                .isEnabled(false)
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

    @Transactional
    public ResponseEntity<?> activateStudent(String token) {
        if(!tokenRepository.existsByToken(token)) {
            return ResponseEntity.status(404).body("Token not found!");
        }

        RegistrationToken registrationToken = tokenRepository.findToken(token)
                .orElseThrow(() -> new IllegalStateException("Token has already been used"));

        if(LocalDateTime.now().isAfter(registrationToken.getExpiresAt())) {
            // TODO: Delete user that is persisted in database so he/she can register again
            return ResponseEntity.status(403).body("Token expired");
        }
        Student student = studentRepository.findByUsername(registrationToken.getUsername());

        try {
            student.setEnabled(true);
            studentRepository.save(student);
            registrationToken.setConfirmed(true);
            tokenRepository.save(registrationToken);
        } catch (Exception e) {
            logger.error("Couldn't persist student or token");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok().build();
    }
}
