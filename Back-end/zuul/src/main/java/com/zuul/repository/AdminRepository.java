package com.zuul.repository;

import com.zuul.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query(value = "SELECT COUNT(*) FROM admin", nativeQuery = true)
    int countAdmins();
}
