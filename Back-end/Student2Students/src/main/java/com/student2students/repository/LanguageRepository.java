package com.student2students.repository;

import com.student2students.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByLanguageName(String languageName);
    Page<Language> findAll(Pageable pageable);
    boolean existsByLanguageName(String languageName);
    boolean existsByLanguageCode(String languageCode);
    long deleteByLanguageCode(String languageCode);

    Optional<Language> findByLanguageCode(String language);
}
