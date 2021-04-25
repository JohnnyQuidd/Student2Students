package com.student2students.postservice.message_broker;

import com.google.gson.Gson;
import com.student2students.postservice.service.MajorService;
import com.student2students.postservice.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    private final TopicService topicService;
    private final MajorService majorService;
    private Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    public MessageListener(TopicService topicService, MajorService majorService) {
        this.topicService = topicService;
        this.majorService = majorService;
    }

    public void listenForMessages(String msgInstruction){
        Gson gson = new Gson();
        MessageInstruction instruction = gson.fromJson(msgInstruction, MessageInstruction.class);

        switch (instruction.getInstructionName()) {
            case "MAJOR_ADDED" : majorService.addNewMajor(instruction.getMajorDTO());
                logger.info("Saving major");
                break;
            case "MAJOR_DELETED" : majorService.deleteMajor(instruction.getMajorDTO().getMajorName());
                logger.info("Deleting major");
                break;
            case "TOPIC_ADDED" : topicService.addNewTopic(instruction.getTopicDTO());
                logger.info("Saving topic");
                break;
            case "TOPIC_DELETED" : topicService.deleteTopic(instruction.getTopicDTO().getTopicName());
                logger.info("Deleting topic");
                break;

            default: logger.info("Couldn't decode message instruction name");
        }

    }
}
