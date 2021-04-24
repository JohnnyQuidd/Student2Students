package com.student2students.postservice.controller;

import com.student2students.postservice.dto.PostCreationDTO;
import com.student2students.postservice.service.PostService;
import com.student2students.postservice.service.StudentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final StudentRequestService requestService;

    @Autowired
    public PostController(PostService postService,
                          StudentRequestService requestService) {
        this.postService = postService;
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<?> createNewPost(@RequestBody PostCreationDTO postCreationDTO, HttpServletRequest request) {
        String username = requestService.getStudentFromRequest(request);
        if(username == null) {
            return ResponseEntity.status(403).build();
        }
        return postService.createNewPost(postCreationDTO, username);
    }
}
