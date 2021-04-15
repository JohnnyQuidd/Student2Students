package com.example.student2students.imageservice.controller;

import com.example.student2students.imageservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<?> postAProfilePhoto(Model model,
                                               @RequestParam("images") MultipartFile[] images,
                                               @PathVariable("username") String username) {

        return imageService.postAProfilePhoto(model, images, username);
    }

    @GetMapping("/profile-image/{username}")
    public ResponseEntity<?> getAProfilePhoto(@PathVariable("username") String username) {
        return imageService.getAProfilePhoto(username);
    }

    @GetMapping("/profile-image/user")
    public ResponseEntity<?> getDefaultUserProfileImage() {
        return imageService.getDefaultUserProfileImage();
    }
}