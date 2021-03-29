package com.student2students.Student2Students.dao;

import com.google.common.collect.Lists;
import com.student2students.Student2Students.auth.ApplicationUser;
import com.student2students.Student2Students.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake_repository")
public class FakeApplicationUserDAO implements ApplicationUserDAO{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDAO(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream().filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "student1",
                        passwordEncoder.encode("student123"),
                        ApplicationUserRole.STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(
                        "admin1",
                        passwordEncoder.encode("admin123"),
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(
                        "admintrainee1",
                        passwordEncoder.encode("admintrainee123"),
                        ApplicationUserRole.ADMIN_TRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );

        return applicationUsers;
    }
}
