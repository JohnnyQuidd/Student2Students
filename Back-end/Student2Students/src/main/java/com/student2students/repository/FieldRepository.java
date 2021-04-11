package com.student2students.repository;

import com.student2students.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    Optional<Field> findByFieldName(String fieldName);
    boolean existsByFieldName(String fieldName);
    long deleteByFieldName(String fieldName);
}
