package com.student2students.Student2Students.dao;

import com.student2students.Student2Students.auth.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserDAO {
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
