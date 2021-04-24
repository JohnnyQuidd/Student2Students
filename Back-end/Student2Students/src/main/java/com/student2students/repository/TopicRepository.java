package com.student2students.repository;

import com.student2students.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTopicName(String topicName);
    long deleteByTopicName(String topicName);
    List<Topic> findByMajor_MajorName(String major);
}