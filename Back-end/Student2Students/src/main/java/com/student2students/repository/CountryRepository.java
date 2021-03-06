package com.student2students.repository;

import com.student2students.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCountry(String country);
    boolean existsByCountry(String country);
    long deleteByCountry(String country);
}
