package com.student2students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationDetailsService implements UserDetailsService {
    private final AdminService adminService;
    private final StudentService studentService;

    @Autowired
    public ApplicationDetailsService(AdminService adminService, StudentService studentService) {
        this.adminService = adminService;
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails studentDetails = studentService.loadUserByUsername(username);
        if(studentDetails != null){
            return studentDetails;
        }

        return adminService.loadUserByUsername(username);
    }
}
