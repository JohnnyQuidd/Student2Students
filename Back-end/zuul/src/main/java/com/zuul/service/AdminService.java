package com.zuul.service;

import com.zuul.dto.AdminRegisterDTO;
import com.zuul.model.Admin;
import com.zuul.model.Student;
import com.zuul.repository.AdminRepository;
import com.zuul.repository.StudentRepository;
import com.zuul.security.ApplicationUserRole;
import com.zuul.util.UniquenessCheck;
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
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        StudentRepository studentRepository,
                        PasswordEncoder passwordEncoder,
                        UniquenessCheck uniquenessCheck) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
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
        Admin admin = Admin.builder()
                .email(adminRegisterDTO.getEmail())
                .username(adminRegisterDTO.getUsername())
                .password(passwordEncoder.encode(adminRegisterDTO.getPassword()))
                .createdAt(LocalDate.now())
                .userRole(ApplicationUserRole.ADMIN)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
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
