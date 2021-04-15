package com.revaturemax.controller;

import com.revaturemax.dto.TopicResponse;
import com.revaturemax.model.CurriculumDay;
import com.revaturemax.model.Quiz;
import com.revaturemax.service.BatchService;
import com.revaturemax.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/batches")
public class BatchController {

    private static final Logger logger = LogManager.getLogger(BatchController.class);

    @Autowired
    private BatchService batchService;

    @Autowired
    private TopicService topicService;

    @GetMapping(value = "/{batch-id}/curriculum", produces = "application/json")
    public List<CurriculumDay> getCurriculum(@PathVariable("batch-id") long batchId) {
        //TODO - authenticate token
        logger.info("GET /batches/{}/curriculum received", batchId);
        //TODO - implement
        return null;
    }

    /**
    //@PostMapping(value = "/{batch-id}/curriculum", produces = "application/json")
    public CurriculumDay postCurriculum(@PathVariable("batch-id") long batchId,
                                        //@RequestBody CurriculumDay curriculumDay) {
        //TODO
        return null;
    }*/

    @PostMapping(value = "/{batch-id}/quizzes")
    public void postQuiz(@PathVariable("batch-id") long batchId,
                         @RequestBody Quiz quiz) {
        //TODO - authenticate token
        logger.info("POST /batches/{}/quizzes received", batchId);
        //TODO - implement
    }

    //put or patch...
    @PutMapping(value = "/{batch-id}/quizzes/{quiz-id}")
    public void putQuiz(@PathVariable("batch-id") long batchId,
                        @PathVariable("quiz-id") long quizId,
                        @RequestBody Quiz quiz) {
        //TODO - authenticate token
        logger.info("PUT /batches/{}/quizzes/{} received", batchId, quizId);
        //TODO - implement
    }

    @DeleteMapping(value = "/{batch-id}/quizzes/{quiz-id}")
    public void deleteQuiz(@PathVariable("batch-id") long batchId,
                           @PathVariable("quiz-id") long quizId) {
        //TODO - authenticate token
        logger.info("DELETE /batches/{}/quizzes/{} received", batchId, quizId);
        //TODO - implement
    }

    @GetMapping(value = "/{batch-id}/topics/{topic-id}", produces = "application/json")
    public TopicResponse getTopic(@PathVariable("batch-id") long batchId,
                                  @PathVariable("topic-id") long topicId) {
        //TODO - authenticate token
        logger.info("GET /batches/{}/topics/{} received", batchId, topicId);
        return topicService.getTopic(batchId, topicId);
    }

}
