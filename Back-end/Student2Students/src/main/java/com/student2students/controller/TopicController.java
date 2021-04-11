package com.student2students.controller;

import com.student2students.dto.TopicDTO;
import com.student2students.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/topic")
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity addNewTopic(@RequestBody TopicDTO topicDTO) {
        return topicService.addNewTopic(topicDTO);
    }

    @DeleteMapping("/{topicName}")
    public ResponseEntity deleteTopic(@PathVariable("topicName") String topicName) {
        return topicService.deleteTopic(topicName);
    }
}
