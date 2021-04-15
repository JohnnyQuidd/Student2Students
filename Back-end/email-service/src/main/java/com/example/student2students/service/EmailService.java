package com.example.student2students.service;

import com.example.student2students.dto.EmailDTO;
import com.example.student2students.model.Email;
import com.example.student2students.repository.EmailRepository;
import com.example.student2students.util.CustomEmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    private final EmailRepository emailRepository;
    private final CustomEmailSender customEmailSender;
    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(EmailRepository emailRepository,
                        CustomEmailSender customEmailSender) {
        this.emailRepository = emailRepository;
        this.customEmailSender = customEmailSender;
    }

    public ResponseEntity<?> sendAnEmail(EmailDTO emailDTO) {
        Email email = Email.builder()
                .receiverEmail(emailDTO.getReceiverEmail())
                .senderEmail("xmlwebservices2020@gmail.com")
                .subject(emailDTO.getSubject())
                .content(emailDTO.getContent())
                .dateTime(LocalDateTime.now())
                .build();

        String formattedText = "<html><body><h3>" + email.getContent() + "</h3></body></html>";

        try {
            emailRepository.save(email);
            return customEmailSender.sendMail(new String[]{email.getReceiverEmail()}, email.getSubject(), formattedText);
        } catch (Exception e) {
            logger.error("Couldn't persist email to database ", email);
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

    }
}
