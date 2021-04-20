package com.student2students.repository;

import com.student2students.registration.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    boolean existsByToken(String token);
    @Query(value = "SELECT * FROM registration_token WHERE token=?1 AND confirmed=false", nativeQuery = true)
    Optional<RegistrationToken> findToken(String token);
}
