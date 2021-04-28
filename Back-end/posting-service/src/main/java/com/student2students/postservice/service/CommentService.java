package com.student2students.postservice.service;

import com.google.gson.Gson;
import com.student2students.postservice.dto.CommentDTO;
import com.student2students.postservice.dto.EmailDTO;
import com.student2students.postservice.message_broker.MessagePublisher;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MessagePublisher publisher;
    private Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          MessagePublisher messagePublisher) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.publisher = messagePublisher;
    }


    @Transactional
    public ResponseEntity<?> createNewComment(CommentDTO commentDTO, String username) {
        Comment comment = createCommentFromDTO(commentDTO, username);
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new IllegalStateException("Unable to find corresponding post!"));
        EmailDTO emailDTO = EmailDTO.builder()
                .receiverEmail(post.getAuthorEmail())
                .receiverFirstName(post.getStudentUsername())
                .content("")
                .subject("New Comment on your post")
                .activationLink("")
                .studentCommenting(username)
                .instruction("COMMENT_EMAIL")
                .build();
        Gson gson = new Gson();
        String emailString = gson.toJson(emailDTO);

        try {
            publisher.sendCommentNotification(emailString);
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

    public ResponseEntity<?> fetchCommentsForPost(String headline) {
        List<Comment> comments = commentRepository.findByPost_Headline(headline);

        return ResponseEntity.ok(createDTOFromCommentList(comments));
    }

    private List<CommentDTO> createDTOFromCommentList(List<Comment> comments) {
        return comments.stream()
                .map(comment -> CommentDTO.builder()
                        .id(comment.getId())
                        .postId(comment.getPost().getId())
                        .username(comment.getUsername())
                        .body(comment.getBody())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
