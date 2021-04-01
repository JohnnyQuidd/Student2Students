package com.student2students.service;

import com.student2students.dao.AdminRegisterDAO;
import com.student2students.model.Admin;
import com.student2students.model.Language;
import com.student2students.repository.AdminRepository;
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
    private final String NOT_FOUND_STRING = "Admin with username %s not found";
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessCheck uniquenessCheck;
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder,
                        UniquenessCheck uniquenessCheck) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.uniquenessCheck = uniquenessCheck;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(NOT_FOUND_STRING, username)));
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
        return Admin.builder()
                .username(adminRegisterDAO.getUsername())
                .firstName(adminRegisterDAO.getFirstName())
                .lastName(adminRegisterDAO.getLastName())
                .city(adminRegisterDAO.getCity())
                .country(adminRegisterDAO.getCountry())
                .email(adminRegisterDAO.getEmail())
                .password(passwordEncoder.encode(adminRegisterDAO.getPassword()))
                .language(Language.valueOf(adminRegisterDAO.getLanguage()))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .userRole(ApplicationUserRole.ADMIN)
                .createdAt(LocalDate.now())
                .language(Language.ENGLISH)
                .build();
    }


}
