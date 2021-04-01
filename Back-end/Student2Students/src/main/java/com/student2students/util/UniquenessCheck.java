package com.student2students.util;

import com.student2students.repository.AdminRepository;
import com.student2students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniquenessCheck {
    private AdminRepository adminRepository;
    private StudentRepository studentRepository;

    @Autowired
    public UniquenessCheck(AdminRepository adminRepository, StudentRepository studentRepository) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
    }

    public boolean isEmailUnique(String email) {
        return !adminRepository.existsByEmail(email) && !studentRepository.existsByEmail(email);
    }

    public boolean isUsernameUnique(String username) {
        return !adminRepository.existsByUsername(username) && !studentRepository.existsByUsername(username);
    }
}
