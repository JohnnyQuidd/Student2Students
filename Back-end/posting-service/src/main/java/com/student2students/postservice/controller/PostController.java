package com.student2students.postservice.controller;

import com.student2students.postservice.constants.RestParameters;
import com.student2students.postservice.dto.PostDTO;
import com.student2students.postservice.dto.PostFilterDTO;
import com.student2students.postservice.service.PostService;
import com.student2students.postservice.service.StudentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> createNewPost(@RequestBody PostDTO postDTO, HttpServletRequest request) {
        String username = requestService.getStudentFromRequest(request);
        if(username == null) {
            return ResponseEntity.status(403).build();
        }
        return postService.createNewPost(postDTO, username);
    }

    @GetMapping("/{headline}")
    public ResponseEntity<?> fetchPostByHeadline(@PathVariable("headline") String headline) {
        return postService.fetchPostByHeadline(headline);
    }

    @GetMapping("/student/{username}")
    public ResponseEntity<?> fetchPostsByUsername(@PathVariable("username") String username) {
        return postService.fetchPostsByUsername(username);
    }

    @GetMapping
    public ResponseEntity<?> fetchPosts(@RequestParam(name = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                        @RequestParam(name = RestParameters.LIMIT, required = false, defaultValue = "0") int limit) {
        return postService.fetchPosts(page, limit);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterPosts(@RequestParam("majorName") String majorName, @RequestParam("topics") String[] topics) {
        PostFilterDTO filterDTO = PostFilterDTO.builder()
                .majorName(majorName)
                .topics(Arrays.stream(topics).collect(Collectors.toList()))
                .build();
        return postService.filterPosts(filterDTO);
    }
}
