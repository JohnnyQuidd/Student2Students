package com.student2students.postservice.controller;

import com.student2students.postservice.dto.PostCreationDTO;
import com.student2students.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> createNewPost(@RequestBody PostCreationDTO postCreationDTO) {
        return postService.createNewPost(postCreationDTO);
    }
}
