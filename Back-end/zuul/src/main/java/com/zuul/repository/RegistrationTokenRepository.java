package com.zuul.repository;

import com.zuul.registration.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface RegistrationTokenRepository extends JpaRepository<Token, Long> {
    boolean existsByToken(String token);
    @Query(value = "SELECT * FROM token WHERE token=?1 AND confirmed=false", nativeQuery = true)
    Optional<Token> findToken(String token);
}
