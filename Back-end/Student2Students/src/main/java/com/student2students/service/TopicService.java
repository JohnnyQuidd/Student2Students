package com.student2students.service;

import com.student2students.dto.TopicDTO;
import com.student2students.model.Field;
import com.student2students.model.Topic;
import com.student2students.repository.FieldRepository;
import com.student2students.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final FieldRepository fieldRepository;
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    public TopicService(TopicRepository topicRepository,
                        FieldRepository fieldRepository) {
        this.topicRepository = topicRepository;
        this.fieldRepository = fieldRepository;
    }


    @Transactional
    public ResponseEntity addNewTopic(TopicDTO topicDTO) {
        if(topicRepository.existsByTopicName(topicDTO.getTopicName())) {
            return ResponseEntity.status(403).body("Topic " + topicDTO.getTopicName() + " already exists!");
        }
        Field field = fieldRepository.findByFieldName(topicDTO.getFieldName())
                .orElseThrow(() -> new IllegalStateException("Couldn't find specified field"));

        Topic topic = Topic.builder()
                .topicName(topicDTO.getTopicName())
                .field(field)
                .build();
        try {
            field.getTopics().add(topic);
            fieldRepository.save(field);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("An error while persisting a field " + field.getFieldName() + " with a topic " + topic.getTopicName());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Couldn't persist field with provided topic!");
        }

    }
}
