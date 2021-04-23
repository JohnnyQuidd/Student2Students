package com.example.student2students.imageservice.controller;

import com.example.student2students.imageservice.service.ImageService;
import com.example.student2students.imageservice.util.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/image-service")
public class ImageController {
    private final ImageService imageService;
    private final Authorization authorization;


    @PostMapping("/profile-image")
    public ResponseEntity<?> postAProfilePhoto(@RequestParam("image") MultipartFile image,
                                               HttpServletRequest request) {
        String username = authorization.getStudentFromRequest(request);
        return imageService.postAProfilePhoto(image, username);
    }

    @PostMapping("/university/{universityName}")
    public ResponseEntity<?> postAUniversityPhoto(@RequestParam("image") MultipartFile image,
                                                  @PathVariable("universityName") String universityName) {
        return imageService.postAUniversityPhoto(image, universityName);
    }

    @GetMapping("/profile-image/{username}")
    public ResponseEntity<?> getProfilePhoto(@RequestParam("username") String username) {
        return imageService.getStudentProfilePhoto(username);
    }

    @GetMapping("/profile-image")
    public ResponseEntity<?> getStudentProfilePhoto(HttpServletRequest request) {
        String username = authorization.getStudentFromRequest(request);
        return imageService.getStudentProfilePhoto(username);
    }

    @GetMapping("/university/profile-image/{universityName}")
    public ResponseEntity<?> getUniversityProfilePhoto(@PathVariable("universityName") String universityName) {
        return imageService.getUniversityProfilePhoto(universityName);
    }
}