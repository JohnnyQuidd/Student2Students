package com.student2students.service;

import com.student2students.dto.TopicDTO;
import com.student2students.message_broker.MessagePublisher;
import com.student2students.model.Major;
import com.student2students.model.Topic;
import com.student2students.repository.MajorRepository;
import com.student2students.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final MajorRepository majorRepository;
    private final MessagePublisher messagePublisher;
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    public TopicService(TopicRepository topicRepository,
                        MajorRepository majorRepository,
                        MessagePublisher messagePublisher) {
        this.topicRepository = topicRepository;
        this.majorRepository = majorRepository;
        this.messagePublisher = messagePublisher;
    }


    @Transactional
    public ResponseEntity addNewTopic(TopicDTO topicDTO) {
        if(topicRepository.existsByTopicName(topicDTO.getTopicName())) {
            return ResponseEntity.status(403).body("Topic " + topicDTO.getTopicName() + " already exists!");
        }
        Major major = majorRepository.findByMajorName(topicDTO.getMajorName())
                .orElseThrow(() -> new IllegalStateException("Couldn't find specified major"));

        Topic topic = Topic.builder()
                .topicName(topicDTO.getTopicName())
                .major(major)
                .build();
        try {
            messagePublisher.sendTopicToPostingService(topicDTO);
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

    public ResponseEntity<?> fetchTopics(int page, int limit) {
        List<TopicDTO> topicDTOList = new ArrayList<>();
        if(page == 0 && limit == 0) {
            topicDTOList = makeDTOFromTopic(topicRepository.findAll());
            return ResponseEntity.ok(topicDTOList);
        }
        Pageable pageableElement = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "topicName"));
        List<Topic> topics = topicRepository.findAll(pageableElement).getContent();
        topicDTOList = makeDTOFromTopic(topics);

        return ResponseEntity.ok(topicDTOList);
    }


    public ResponseEntity<?> fetchTopicsByMajorName(String major) {
        List<Topic> topics = topicRepository.findByMajor_MajorName(major);
        List<TopicDTO> topicDTOS = makeDTOFromTopic(topics);

        return ResponseEntity.ok(topicDTOS);
    }

    private List<TopicDTO> makeDTOFromTopic(List<Topic> topicList) {
        return  topicList.stream()
                .map(topic -> TopicDTO.builder()
                        .topicName(topic.getTopicName())
                        .build())
                .collect(Collectors.toList());
    }
}
