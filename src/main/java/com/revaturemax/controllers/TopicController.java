package com.revaturemax.controllers;

import com.revaturemax.dto.TopicRequest;
import com.revaturemax.models.Role;
import com.revaturemax.models.Topic;
import com.revaturemax.models.TopicTag;
import com.revaturemax.services.TopicService;
import com.revaturemax.util.JwtUtil;
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

    @Autowired
    JwtUtil jwtUtil;


    // all '/tags' methods are implemented for testing features

    @GetMapping("/tags")
    public ResponseEntity<List<TopicTag>> getTopicTags(){
        return ResponseEntity.ok().body(topicService.getTopicTags());
    }

    @PostMapping("/tags")
    public ResponseEntity<TopicTag> postNewTopicTag(@RequestBody TopicTag tag){
        TopicTag newTag = topicService.createTag(tag);
        return new ResponseEntity<>(newTag, HttpStatus.CREATED);
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<HttpStatus> deleteTopicTag(@PathVariable long id){
        topicService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    /*
     * mapping for /topics
     * Designed for CRUD individual topics
     */

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics(){
        return ResponseEntity.ok().body(topicService.getAll());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> postNewTopic(@RequestBody Topic topic, @RequestHeader("Authorization")String token){
        if(jwtUtil.getRoleFromToken(token).equals(Role.INSTRUCTOR)){
            logger.info("Instructor creating new topic");
            topicService.create(topic);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    /*
     * Put mapping for /topics/:id
     * Designed for updating individual topics
     * @param Topic DTO containing tag and assigned date.
     */

    @PutMapping
    public ResponseEntity<HttpStatus> updateTopic(@PathVariable long id, @RequestBody TopicRequest topic, @RequestHeader("Authorization")String token){
        if(jwtUtil.getRoleFromToken(token).equals(Role.INSTRUCTOR)){
            logger.info("Instructor creating new topic");
            topicService.update(id, topic);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTopic(@PathVariable long id, @RequestHeader("Authorization")String token){
        if(jwtUtil.getRoleFromToken(token).equals(Role.INSTRUCTOR)){
            logger.info("Deleting topic from DB");
            topicService.delete(id);
        }

    }

}
