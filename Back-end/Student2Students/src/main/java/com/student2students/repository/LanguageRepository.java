package com.student2students.repository;

import com.student2students.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByLanguage(String language);
}
