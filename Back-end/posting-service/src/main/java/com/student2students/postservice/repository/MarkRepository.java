package com.student2students.postservice.repository;

import com.student2students.postservice.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
}
