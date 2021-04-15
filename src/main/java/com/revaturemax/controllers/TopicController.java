package com.revaturemax.controllers;

import com.revaturemax.dto.TopicRequest;
import com.revaturemax.dto.TopicUpdateRequest;
import com.revaturemax.model.Topic;
import com.revaturemax.model.TopicTag;
import com.revaturemax.services.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/topics")
public class TopicController {

    private static final Logger logger = LogManager.getLogger(TopicController.class);

    @Autowired
    private TopicService topicService;

    @GetMapping("/tags")
    public ResponseEntity<List<TopicTag>> getTopicTags(){
        return ResponseEntity.ok().body(topicService.getTopicTags());
    }


    @PostMapping("/tags")
    public ResponseEntity<TopicTag> postNewTopicTag(@RequestBody String tagName){
        TopicTag tag = topicService.createTag(tagName);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }


    /*
     * Post mapping for /topics
     * Designed for creating individual topics
     * @param Topic DTO containing tag and assigned date.
     */

    @PostMapping
    public ResponseEntity<Topic> postNewTopic(@RequestBody TopicRequest topic){
        //authenticate instructor
        logger.info("Instructor creating new topic");
        Topic newTopic = topicService.create(topic);
        return new ResponseEntity<>(newTopic, HttpStatus.CREATED);
    }

    /*
     * Put mapping for /topics
     * Designed for updating individual topics
     * @param Topic DTO containing tag and assigned date.
     */

    @PutMapping
    public ResponseEntity<Topic> updateTopic(@RequestBody TopicUpdateRequest topic){
        //authenticate instructor
        logger.info("Instructor creating new topic");
        return ResponseEntity.ok().body(topicService.update(topic));
    }

}
