package com.student2students.postservice.repository;

import com.student2students.postservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMajor_MajorName(String majorName);
    boolean existsByHeadline(String headline);
    Optional<Post> findByHeadline(String headline);
}
