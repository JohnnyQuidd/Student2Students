package com.student2students.postservice.controller;

import com.student2students.postservice.dto.CommentDTO;
import com.student2students.postservice.service.CommentService;
import com.student2students.postservice.service.StudentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final StudentRequestService requestService;

    @Autowired
    public CommentController(CommentService commentService,
                             StudentRequestService requestService) {
        this.commentService = commentService;
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<?> createNewComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        String username = requestService.getStudentFromRequest(request);
        if(username == null) {
            return ResponseEntity.status(403).body("Invalid username!");
        }
        return commentService.createNewComment(commentDTO, username);
    }

    @GetMapping("/{postHeadline}")
    public ResponseEntity<?> fetchCommentsForPost(@PathVariable("postHeadline") String headline) {
        return commentService.fetchCommentsForPost(headline);
    }
}
