package com.zuul.MockData;

import com.zuul.model.Admin;
import com.zuul.model.Student;
import com.zuul.repository.AdminRepository;
import com.zuul.repository.StudentRepository;
import com.zuul.security.ApplicationUserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RunAfterStartup {
    private Logger logger = LoggerFactory.getLogger(RunAfterStartup.class);
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public RunAfterStartup(AdminRepository adminRepo, StudentRepository studentRepo) {
        this.adminRepository = adminRepo;
        this.studentRepository = studentRepo;
    }

    @EventListener
    public void runAfterStartup(ApplicationStartedEvent event) {
        if(adminRepository.countAdmins() == 0) {
            logger.info("Inserting admins");
            insertAdmin();
        } else {
            logger.info("Not inserting admins");
        }

        if(studentRepository.countStudents() == 0) {
            logger.info("Inserting students");
            insertStudents();
        } else {
            logger.info("Not inserting students");
        }



    }

    private final void insertAdmin() {
        Admin admin = Admin.builder()
                .email("lazar@gmail.com")
                .username("admin")
                .password("$2y$10$R0sLZ23c4WBaYSEjAtUOnunPdKB.F3dKW6D1gyHd/ZARx/38FZ9.y")
                .createdAt(LocalDate.now())
                .userRole(ApplicationUserRole.ADMIN)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .biography("I am admin of Student2Students platform for a very long time.")
                .build();
        adminRepository.save(admin);
    }

    private final void insertStudents() {
        Student ivan91 = Student.builder()
                .email("student@gmail.com")
                .firstName("Ivan")
                .username("ivan91")
                .password("$2y$10$43l3Zf24uUonu6UpbpxtJOPOmaHblcJysD3EMq.l5GPC6dO8tj3o6")
                .createdAt(LocalDate.now())
                .userRole(ApplicationUserRole.STUDENT)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        Student linus = Student.builder()
                .email("torvaldslinusrs@gmail.com")
                .firstName("Linus")
                .username("linus69")
                .password("$2y$10$ADjXMrTYzCHaK8iy5SZxhuAqJwSH/N2rr4NM7XGBnrS2LAl0clFk.")
                .createdAt(LocalDate.now())
                .userRole(ApplicationUserRole.STUDENT)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        studentRepository.save(ivan91);
        studentRepository.save(linus);
    }
}
