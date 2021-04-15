package com.revaturemax.controller;

import com.revaturemax.dto.TopicResponse;
import com.revaturemax.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/topics")
public class TopicController {

    private static final Logger logger = LogManager.getLogger(TopicController.class);

    @Autowired
    private TopicService topicService;

    @GetMapping(value = "/{topic-id}", produces = "application/json")
    public TopicResponse getTopic(@PathVariable("topic-id") long topicId) {
        //authenticate token
        logger.info("GET topics/{} received", topicId);
        long batchId = 1;
        return topicService.getTopic(batchId, topicId);
    }

}
