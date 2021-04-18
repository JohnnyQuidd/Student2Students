package com.example.student2students.imageservice.controller;

import com.example.student2students.imageservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image-service")
public class ImageController {
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/profile-image/{username}")
    public ResponseEntity<?> postAProfilePhoto(@RequestParam("image") MultipartFile image,
                                               @PathVariable("username") String username) {

        return imageService.postAProfilePhoto(image, username);
    }

    @PostMapping("/university/{universityName}")
    public ResponseEntity<?> postAUniversityPhoto(@RequestParam("image") MultipartFile image,
                                                  @PathVariable("universityName") String universityName) {
        return imageService.postAUniversityPhoto(image, universityName);
    }

    @GetMapping("/profile-image/{username}")
    public ResponseEntity<?> getAProfilePhoto(@PathVariable("username") String username) {
        return imageService.getStudentProfilePhoto(username);
    }

    @GetMapping("/university/profile-image/{universityName}")
    public ResponseEntity<?> getUniversityProfilePhoto(@PathVariable("universityName") String universityName) {
        return imageService.getUniversityProfilePhoto(universityName);
    }
}