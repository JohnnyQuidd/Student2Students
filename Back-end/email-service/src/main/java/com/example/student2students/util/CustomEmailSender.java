package com.example.student2students.util;

import com.example.student2students.model.Email;
import com.example.student2students.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class CustomEmailSender {
    private EmailRepository emailRepository;
    private JavaMailSender javaMailSender;
    private Logger logger = LoggerFactory.getLogger(CustomEmailSender.class);

    @Autowired
    public CustomEmailSender(JavaMailSender jmSender, EmailRepository emailRepository){
        javaMailSender = jmSender;
        this.emailRepository = emailRepository;
    }

    public ResponseEntity<?> sendMail(String[] receivers, String subject, String message){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setText(message, true);
            helper.setTo(receivers);
            helper.setSubject(subject);
            helper.setFrom("xmlwebservices2020@gmail.com");

            javaMailSender.send(mimeMessage);
            return new ResponseEntity<>("Email sent", HttpStatus.CREATED);
        } catch (MessagingException e) {
            logger.error("Unable to send email " + e.getMessage());
            return new ResponseEntity<>("Email couldn't be sent", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
