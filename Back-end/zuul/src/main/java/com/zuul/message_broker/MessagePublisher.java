package com.zuul.message_broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {
    private RabbitTemplate rabbitTemplate;

    private final String EMAIL_EXCHANGE_NAME = "email-service-exchange";
    private final String STUDENT_EXCHANGE_NAME = "student-event-exchange";
    private Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    public MessagePublisher(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    public void sendEmailToTheQueue(String emailDTO) {
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE_NAME, "email.service.data", emailDTO);
        logger.info("Message has been sent to queue!");
    }

    public void sendStudentToManageService(String studentRegisterDTO) {
        rabbitTemplate.convertAndSend(STUDENT_EXCHANGE_NAME, "student.event.data", studentRegisterDTO);
        logger.info("Sending studentDTO");
    }
}
