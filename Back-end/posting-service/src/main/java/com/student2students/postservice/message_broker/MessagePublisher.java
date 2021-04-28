package com.student2students.postservice.message_broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {
    private RabbitTemplate rabbitTemplate;

    private final String EMAIL_EXCHANGE_NAME = "email-service-exchange";
    private Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    public MessagePublisher(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    public void sendCommentNotification(String emailDTO) {
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE_NAME, "email.service.data", emailDTO);
        logger.info("Comment email has been sent to queue!");
    }

}
