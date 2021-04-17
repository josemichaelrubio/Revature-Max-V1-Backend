package com.revaturemax.controllers;

import com.revaturemax.dto.BatchResponse;
import com.revaturemax.dto.TopicResponse;
import com.revaturemax.models.EmployeeQuiz;
import com.revaturemax.models.Quiz;
import com.revaturemax.services.BatchService;
import com.revaturemax.services.QuizService;
import com.revaturemax.services.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batches")
@CrossOrigin
public class BatchController {

    private static Logger logger = LogManager.getLogger(BatchController.class);

    @Autowired
    private BatchService batchService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private TopicService topicService;

    @GetMapping(value = "/{batch-id}", produces = "application/json")
    public BatchResponse handleGetBatchInfoById(@PathVariable("batch-id") long id) {
        return batchService.getBatchInfoAndAveragesById(id);
    }

    @GetMapping(value = "/{batch-id}/curriculum", produces = "application/json")
    public List<EmployeeQuiz> getCurriculum(@PathVariable("batch-id") long batchId) {
        //TODO - authenticate token
        logger.info("GET /batches/{}/curriculum received", batchId);
        //return batchService.getCurriculum(batchId);
        return quizService.getEmployeeQuizzes(batchId);
    }

    @PostMapping(value = "/{batch-id}/quizzes")
    public void postQuiz(@PathVariable("batch-id") long batchId,
                         @RequestBody Quiz quiz) {
        //TODO - authenticate token
        logger.info("POST /batches/{}/quizzes received", batchId);
        quizService.setNewQuiz(batchId, quiz);
    }

    @PutMapping(value = "/{batch-id}/quizzes/{quiz-id}")
    public void putQuiz(@PathVariable("batch-id") long batchId,
                        @PathVariable("quiz-id") long quizId,
                        @RequestBody Quiz quiz) {
        //TODO - authenticate token
        logger.info("PUT /batches/{}/quizzes/{} received", batchId, quizId);
        quiz.setId(quizId);
        quizService.updateQuiz(batchId, quiz);
    }

    @DeleteMapping(value = "/{batch-id}/quizzes/{quiz-id}")
    public void deleteQuiz(@PathVariable("batch-id") long batchId,
                           @PathVariable("quiz-id") long quizId) {
        //TODO - authenticate token
        logger.info("DELETE /batches/{}/quizzes/{} received", batchId, quizId);
        quizService.removeQuiz(batchId, quizId);
    }

    @GetMapping(value = "/{batch-id}/topics/{topic-id}", produces = "application/json")
    public TopicResponse getTopic(@PathVariable("batch-id") long batchId,
                                  @PathVariable("topic-id") long topicId) {
        //TODO - authenticate token
        logger.info("GET /batches/{}/topics/{} received", batchId, topicId);
        return topicService.getTopic(batchId, topicId);
    }

}
