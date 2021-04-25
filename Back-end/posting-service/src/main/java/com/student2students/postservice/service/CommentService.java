package com.student2students.postservice.service;

import com.student2students.postservice.dto.CommentDTO;
import com.student2students.postservice.model.Comment;
import com.student2students.postservice.model.Post;
import com.student2students.postservice.repository.CommentRepository;
import com.student2students.postservice.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @Transactional
    public ResponseEntity<?> createNewComment(CommentDTO commentDTO, String username) {
        Comment comment = createCommentFromDTO(commentDTO, username);
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new IllegalStateException("Unable to find corresponding post!"));
        try {
            comment.setPost(post);
            commentRepository.save(comment);
            return ResponseEntity.status(201).body("Comment added successfully");
        } catch (Exception e) {
            logger.error("Unable to persist comment");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private Comment createCommentFromDTO(CommentDTO commentDTO, String username) {
        return Comment.builder()
                .username(username)
                .body(commentDTO.getBody())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
