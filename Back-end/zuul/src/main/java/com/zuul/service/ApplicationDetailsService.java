package com.zuul.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationDetailsService implements UserDetailsService {
    private final AdminService adminService;
    private final StudentService studentService;
    private Logger logger = LoggerFactory.getLogger(ApplicationDetailsService.class);

    @Autowired
    public ApplicationDetailsService(AdminService adminService, StudentService studentService) {
        this.adminService = adminService;
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info(username);
        UserDetails studentDetails = studentService.loadUserByUsername(username);
        if(studentDetails != null){
            logger.info("Found " + studentDetails.getUsername() + " | " + studentDetails.getPassword());
            return studentDetails;
        }

        logger.info("Not found");
        return adminService.loadUserByUsername(username);
    }
}
