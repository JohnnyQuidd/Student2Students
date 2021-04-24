package com.student2students.postservice.service;

import com.student2students.postservice.dto.PostCreationDTO;
import com.student2students.postservice.model.Major;
import com.student2students.postservice.model.Post;
import com.student2students.postservice.model.Topic;
import com.student2students.postservice.repository.MajorRepository;
import com.student2students.postservice.repository.PostRepository;
import com.student2students.postservice.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MajorRepository majorRepository;
    private final TopicRepository topicRepository;
    private Logger logger = LoggerFactory.getLogger(PostService.class);


    @Autowired
    public PostService(PostRepository postRepository,
                       MajorRepository majorRepository,
                       TopicRepository topicRepository) {
        this.postRepository = postRepository;
        this.majorRepository = majorRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public ResponseEntity<?> createNewPost(PostCreationDTO postDTO, String username) {
        Post post = createPostFromDTO(postDTO, username);

        try {
            postRepository.save(post);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("Couldn't persist post " + post);
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private Post createPostFromDTO(PostCreationDTO postDTO, String username) {
        Major major = majorRepository.findByMajorName(postDTO.getMajorName())
                .orElseThrow(() -> new IllegalStateException("Major name " + postDTO.getMajorName() + " not found!"));
        Set<Topic> topics = postDTO.getTopics().stream()
                .map(topicName -> topicRepository.findByTopicName(topicName)
                        .orElseThrow(() -> new IllegalStateException("Topic " + topicName + " not found!")))
                .collect(Collectors.toSet());


        return Post.builder()
                .studentUsername(username)
                .headline(postDTO.getHeadline())
                .body(postDTO.getBody())
                .createdAt(LocalDateTime.now())
                .major(major)
                .topics(topics)
                .build();
    }
}
