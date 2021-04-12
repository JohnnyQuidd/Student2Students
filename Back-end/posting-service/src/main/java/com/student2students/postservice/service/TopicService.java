package com.student2students.postservice.service;

import com.student2students.postservice.dto.TopicDTO;
import com.student2students.postservice.model.Major;
import com.student2students.postservice.model.Topic;
import com.student2students.postservice.repository.MajorRepository;
import com.student2students.postservice.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final MajorRepository majorRepository;
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    public TopicService(TopicRepository topicRepository,
                        MajorRepository majorRepository) {
        this.topicRepository = topicRepository;
        this.majorRepository = majorRepository;
    }


    @Transactional
    public ResponseEntity addNewTopic(TopicDTO topicDTO) {
        if(topicRepository.existsByTopicName(topicDTO.getTopicName())) {
            return ResponseEntity.status(403).body("Topic " + topicDTO.getTopicName() + " already exists!");
        }
        Major major = majorRepository.findByMajorName(topicDTO.getMajorName())
                .orElseThrow(() -> new IllegalStateException("Couldn't find specified major: " + topicDTO.getMajorName()));

        Topic topic = Topic.builder()
                .topicName(topicDTO.getTopicName())
                .major(major)
                .build();
        try {
            major.getTopics().add(topic);
            majorRepository.save(major);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("An error while persisting a major " + major.getMajorName() + " with a topic " + topic.getTopicName());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Couldn't persist field with provided topic!");
        }

    }

    @Transactional
    public ResponseEntity deleteTopic(String topicName) {
        if(!topicRepository.existsByTopicName(topicName)) {
            return ResponseEntity.status(403).body("Topic " + topicName + " not found!");
        }

        try {
            topicRepository.deleteByTopicName(topicName);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            logger.error("Couldn't delete topic " + topicName);
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
