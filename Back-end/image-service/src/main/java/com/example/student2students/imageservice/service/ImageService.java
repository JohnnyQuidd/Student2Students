package com.example.student2students.imageservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {
    public static final String uploadDirectory = System.getProperty("user.dir") + "/user-images";
    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    public ResponseEntity<?> postAProfilePhoto(Model model, MultipartFile[] images, String username) {

        StringBuilder imageNames = new StringBuilder();
        for(MultipartFile image : images) {
            Path path = Paths.get(uploadDirectory, username + ".png");
            imageNames.append(image.getOriginalFilename());
            try {
                logger.info("Saving image at location: " + uploadDirectory);
                Files.write(path, image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Unable to save image to specified path");
                return new ResponseEntity<>("Server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Image successfully saved", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAProfilePhoto(String username) {
        File rootFile = new File(uploadDirectory);
        if(rootFile != null) {
            for (File file : rootFile.listFiles()) {
                if(file.getName().equals(username + ".png")) {
                    try {
                        String extension = "png";
                        FileInputStream iStream = new FileInputStream(file);
                        byte[] bytes = new byte[(int) file.length()];
                        iStream.read(bytes);
                        String encodeBase64 = Base64.getEncoder().encodeToString(bytes);
                        String finalString = "data:image/" + extension + ";base64," + encodeBase64 ;
                        iStream.close();

                        return new ResponseEntity<>(finalString, HttpStatus.OK);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception occurred");
                    }
                }
            }
            return new ResponseEntity<>("Profile picture not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Could not find folder", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getDefaultUserProfileImage() {
        File rootFile = new File(uploadDirectory);
        if(rootFile != null) {
            for(File file : rootFile.listFiles()) {
                if(file.getName().equals("default-user-image.png")) {
                    try {
                        FileInputStream inputStream = new FileInputStream(file);
                        byte[] bytes = new byte[(int) file.length()];
                        inputStream.read(bytes);
                        String encodeBase64 = Base64.getEncoder().encodeToString(bytes);
                        String finalString = "data:image/png;base64," + encodeBase64;
                        inputStream.close();

                        return new ResponseEntity<>(finalString, HttpStatus.OK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return new ResponseEntity<>("Profile picture not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Could not find folder", HttpStatus.NOT_FOUND);
    }
}
