package com.student2students.postservice.service;

import com.student2students.postservice.dto.PostDTO;
import com.student2students.postservice.dto.PostFilterDTO;
import com.student2students.postservice.model.Major;
import com.student2students.postservice.model.Post;
import com.student2students.postservice.model.Topic;
import com.student2students.postservice.repository.MajorRepository;
import com.student2students.postservice.repository.PostRepository;
import com.student2students.postservice.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<?> createNewPost(PostDTO postDTO, String username) {
        Post post = createPostFromDTO(postDTO, username);
        if(postRepository.existsByHeadline(post.getHeadline())) {
            return ResponseEntity.status(403).body("Post with provided headline already exists");
        }
        try {
            postRepository.save(post);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("Couldn't persist post " + post);
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private Post createPostFromDTO(PostDTO postDTO, String username) {
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

    public ResponseEntity<?> fetchPosts(int page, int limit) {
        List<Post> posts = new ArrayList<>();
        List<PostDTO> dtos = new ArrayList<>();

        if(page == 0 && limit == 0) {
            posts = postRepository.findAll();
            dtos = createDTOListFromPostList(posts);
        } else {
            Pageable pageElement = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
            posts = postRepository.findAll(pageElement).getContent();
            dtos = createDTOListFromPostList(posts);
        }


        return ResponseEntity.ok(dtos);
    }


    public ResponseEntity<?> filterPosts(PostFilterDTO filterDTO) {
        List<Post> posts = new ArrayList<>();
        if(!filterDTO.getMajorName().equals("")) {
            posts = postRepository.findByMajor_MajorName(filterDTO.getMajorName());
        } else {
            posts = postRepository.findAll();
        }
        if(!filterDTO.getTopics().isEmpty()) {
            posts = filterPostsByTopics(posts, filterDTO.getTopics());
        }
        List<PostDTO> dtos = createDTOListFromPostList(posts);
        return ResponseEntity.ok(dtos);
    }


    private List<Post> filterPostsByTopics(List<Post> posts, List<String> topics) {
        return posts.stream()
                .filter(post -> post.getTopics()
                        .stream()
                        .anyMatch(topic -> {
                            for(String topicName : topics) {
                                if(topicName.equals(topic.getTopicName()))
                                    return true;
                            }
                            return false;
                        }))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> fetchPostByHeadline(String headline) {
        Post post = postRepository.findByHeadline(headline)
                .orElseThrow(() -> new IllegalStateException("Post with provided headline does not exist"));

        PostDTO postDTO = createDTOFromPost(post);
        return ResponseEntity.status(200).body(postDTO);
    }

    private PostDTO createDTOFromPost(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .username(post.getStudentUsername())
                .createdAt(post.getCreatedAt())
                .headline(post.getHeadline())
                .body(post.getBody())
                .majorName(post.getMajor().getMajorName())
                .topics(post.getTopics().stream()
                        .map(topic -> topic.getTopicName())
                        .collect(Collectors.toList()))
                .build();
    }

    public ResponseEntity<?> fetchPostsByUsername(String username) {
        List<Post> posts = postRepository.findByStudentUsername(username);
        List<PostDTO> dtos = createDTOListFromPostList(posts);

        return ResponseEntity.ok(dtos);
    }

    private List<PostDTO> createDTOListFromPostList(List<Post> posts) {
        return posts.stream()
                .map(post -> PostDTO.builder()
                        .id(post.getId())
                        .username(post.getStudentUsername())
                        .createdAt(post.getCreatedAt())
                        .headline(post.getHeadline())
                        .body(post.getBody())
                        .majorName(post.getMajor().getMajorName())
                        .topics(post.getTopics().stream()
                                .map(topic -> topic.getTopicName())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
