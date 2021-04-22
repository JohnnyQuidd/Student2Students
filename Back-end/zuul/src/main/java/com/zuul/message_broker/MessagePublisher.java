package com.zuul.message_broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {
    public static final String TOPIC_EXCHANGE_NAME = "student-event-exchange";
    private RabbitTemplate rabbitTemplate;
    private Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    public MessagePublisher(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    public void sendEmailToTheQueue(String studentRegisterDTO) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "student.update.data", studentRegisterDTO);
        logger.info("Message has been sent to queue!");
    }
}
