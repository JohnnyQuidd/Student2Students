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

    @PostMapping("/profile-image/{advertUUID}")
    public ResponseEntity<?> postAProfilePhoto(Model model,
                                               @RequestParam("images") MultipartFile[] images) {

        return imageService.postAProfilePhoto(model, images);
    }

    @GetMapping("/profile-image/{advertUUID}")
    public ResponseEntity<?> getAProfilePhoto(@PathVariable("advertUUID") String uuid) {
        return imageService.getAProfilePhoto(uuid);
    }

    @GetMapping("/profile-image/user")
    public ResponseEntity<?> getDefaultUserProfileImage() {
        return imageService.getDefaultUserProfileImage();
    }
}