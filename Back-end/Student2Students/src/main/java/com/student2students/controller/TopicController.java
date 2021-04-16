package com.student2students.controller;

import com.student2students.constants.RestParameters;
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

    @GetMapping
    public ResponseEntity<?> fetchTopics(@RequestParam(name = RestParameters.PAGE, required = false, defaultValue = "0") int page,
                                         @RequestParam(name = RestParameters.LIMIT, required = false, defaultValue = "10") int limit) {
        return topicService.fetchTopics(page, limit);
    }

    @PostMapping
    public ResponseEntity<?> addNewTopic(@RequestBody TopicDTO topicDTO) {
        return topicService.addNewTopic(topicDTO);
    }

    @DeleteMapping("/{topicName}")
    public ResponseEntity<?> deleteTopic(@PathVariable("topicName") String topicName) {
        return topicService.deleteTopic(topicName);
    }
}
