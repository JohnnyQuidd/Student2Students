package com.zuul.service;

import com.google.gson.Gson;
import com.zuul.dto.EmailDTO;
import com.zuul.dto.StudentRegisterDTO;
import com.zuul.model.Student;
import com.zuul.registration.Token;
import com.zuul.repository.StudentRepository;
import com.zuul.repository.RegistrationTokenRepository;
import com.zuul.security.ApplicationUserRole;
import com.zuul.util.UniquenessCheck;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final RegistrationTokenRepository registrationTokenRepository;
    //private final MessagePublisher messagePublisher;
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
        Token token = Token.builder()
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
            // TODO: Send EmailDTO message to the Queue
            // messagePublisher.sendEmailToTheQueue(emailGson);
            registrationTokenRepository.save(token);
            studentRepository.save(student);
        } catch(Exception e) {
            logger.error("Couldn't persist student");
            e.printStackTrace();
            return ResponseEntity.status(500).body("Couldn't persist student");
        }

        return ResponseEntity.status(201).body("Student registered");
    }

    private Student createStudentFromDTO(StudentRegisterDTO studentDTO) {
        return Student.builder()
                .email(studentDTO.getEmail())
                .firstName(studentDTO.getFirstName())
                .username(studentDTO.getUsername())
                .password(passwordEncoder.encode(studentDTO.getPassword()))
                .userRole(ApplicationUserRole.STUDENT)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(false)
                .createdAt(LocalDate.now())
                .build();
    }


    @Transactional
    public ResponseEntity<?> activateStudent(String token) {
        if(!registrationTokenRepository.existsByToken(token)) {
            return ResponseEntity.status(404).body("Token not found!");
        }

        Token registrationToken = registrationTokenRepository.findToken(token)
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
            registrationTokenRepository.save(registrationToken);
        } catch (Exception e) {
            logger.error("Couldn't persist student or token");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok().build();
    }
}
