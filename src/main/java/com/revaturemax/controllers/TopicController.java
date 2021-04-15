package com.revaturemax.controllers;

import com.revaturemax.dto.TopicRequest;
import com.revaturemax.model.Topic;
import com.revaturemax.services.TopicService;
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

    @PostMapping
    public Topic postNewTopic(@RequestBody TopicRequest topic){
        //authenticate instructor
        logger.info("Instructor creating new topic");
        return topicService.create(topic);
    }

    /*
     * Put mapping for /topics
     * Designed for updating individual topics
     * @param Topic DTO containing tag and assigned date.
     */

}
