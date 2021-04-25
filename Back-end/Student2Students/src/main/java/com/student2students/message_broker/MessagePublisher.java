package com.student2students.message_broker;

import com.google.gson.Gson;
import com.student2students.dto.MajorDTO;
import com.student2students.dto.TopicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {
    public static final String TOPIC_EXCHANGE_NAME = "student-update-exchange";
    public static final String POSTING_TOPC = "posting-service-exchange";
    private RabbitTemplate rabbitTemplate;
    private Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    public MessagePublisher(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    public void sendEmailToTheQueue(String emailGson) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "student.update.data", emailGson);
        logger.info("Message has been sent to queue!");
    }

    public void sendMajorToPostingService(MajorDTO majordto) {
        MessageInstruction instruction = MessageInstruction.builder()
                .instructionName("MAJOR_ADDED")
                .majorDTO(majordto)
                .build();
        Gson gson = new Gson();
        rabbitTemplate.convertAndSend(POSTING_TOPC, "posting.service.major", gson.toJson(instruction));
        logger.info("Sending major");
    }

    public void sendMajorDeletedToPostingService(MajorDTO majorDTO) {
        MessageInstruction instruction = MessageInstruction.builder()
                .instructionName("MAJOR_DELETED")
                .majorDTO(majorDTO)
                .build();
        Gson gson = new Gson();
        rabbitTemplate.convertAndSend(POSTING_TOPC, "posting.service.major", gson.toJson(instruction));
        logger.info("Sending major");
    }

    public void sendTopicToPostingService(TopicDTO topicDTO) {
        MessageInstruction instruction = MessageInstruction.builder()
                .instructionName("TOPIC_ADDED")
                .topicDTO(topicDTO)
                .build();
        Gson gson = new Gson();
        rabbitTemplate.convertAndSend(POSTING_TOPC, "posting.service.topic", gson.toJson(instruction));
        logger.info("Sending topic");
    }
    
    public void sendTopicDeletedToPostingService(TopicDTO topicDTO) {
        MessageInstruction instruction = MessageInstruction.builder()
                .instructionName("TOPIC_DELETED")
                .topicDTO(topicDTO)
                .build();
        Gson gson = new Gson();
        rabbitTemplate.convertAndSend(POSTING_TOPC, "posting.service.topic", gson.toJson(instruction));
        logger.info("Sending topic");
    }
}
