package com.example.student2students.message_broker;

import com.example.student2students.dto.EmailDTO;
import com.example.student2students.service.EmailService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);
    private final EmailService emailService;

    @Autowired
    public MessageListener(EmailService emailService) {
        this.emailService = emailService;
    }

    public void listenForMessages(String emailGson){
        Gson gson = new Gson();
        EmailDTO emailDTO = gson.fromJson(emailGson, EmailDTO.class);

        switch (emailDTO.getInstruction()) {
            case "AUTHENTICATION_EMAIL": sendActivationEmail(emailDTO);
                break;
            case "COMMENT_EMAIL": sendCommentingEmail(emailDTO);
                break;
            default: logger.info("Couldn't decode message");
        }
    }

    private void sendActivationEmail(EmailDTO emailDTO) {
        logger.info("Sending activation email " + emailDTO);
        emailService.sendActivationEmail(emailDTO);
    }

    private void sendCommentingEmail(EmailDTO emailDTO) {
        logger.info("Sending commenting email");
        emailService.sendCommentingEmail(emailDTO);
    }
}
