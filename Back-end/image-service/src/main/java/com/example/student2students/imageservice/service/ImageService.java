package com.example.student2students.imageservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImageService {
    public static final String studentDirectory = System.getProperty("user.dir") + "/user-images";
    public static final String universityDirectory = System.getProperty("user.dir") + "/university-images";
    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    public ResponseEntity<?> postAProfilePhoto(MultipartFile image, String username) {
        checkIfFolderExists(studentDirectory);
        return postAPhoto(studentDirectory, image, username);
    }

    public ResponseEntity<?> postAUniversityPhoto(MultipartFile image, String universityName) {
        checkIfFolderExists(universityDirectory);
        return postAPhoto(universityDirectory, image, universityName);
    }

    private void checkIfFolderExists(String directoryName) {
        File directory = new File(directoryName);
        if(!directory.exists()) {
            directory.mkdir();
        }
    }

    public ResponseEntity<?> postAPhoto(String directoryPath, MultipartFile image, String name) {
        StringBuilder imageName = new StringBuilder();
        String imageExtension = image.getOriginalFilename().split("\\.")[1];
        logger.info(imageExtension);
        Path path = Paths.get(directoryPath, name + "." +imageExtension);
        imageName.append(image.getOriginalFilename());

        try {
            logger.info("Saving image at location: " + directoryPath);
            Files.write(path, image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Unable to save image to specified path");
            return new ResponseEntity<>("Server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>("Image successfully saved", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getStudentProfilePhoto(String username) {
        return getAProfilePhoto(studentDirectory, username);
    }

    public ResponseEntity<?> getUniversityProfilePhoto(String universityName) {
        return getAProfilePhoto(universityDirectory, universityName);
    }

    public ResponseEntity<?> getAProfilePhoto(String path, String fileName) {
        File rootFile = new File(path);
        if(rootFile != null) {
            for (File file : rootFile.listFiles()) {
                if(file.getName().equals(fileName + ".png")
                    || file.getName().equals(fileName + ".jpg")
                    || file.getName().equals(fileName + ".jpeg")) {
                    try {
                        String extension = file.getName().split("\\.")[1];
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


}
