package com.revaturemax.controllers;

import com.revaturemax.dto.BatchResponse;
import com.revaturemax.models.Employee;
import com.revaturemax.dto.CurriculumRequest;
import com.revaturemax.models.Quiz;
import com.revaturemax.models.Role;
import com.revaturemax.services.BatchService;
import com.revaturemax.services.CurriculumService;
import com.revaturemax.services.QuizService;
import com.revaturemax.services.TopicService;
import com.revaturemax.util.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/batches")
@CrossOrigin
public class BatchController {

    private static final Logger logger = LogManager.getLogger(BatchController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BatchService batchService;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private TopicService topicService;

    @ResponseBody
    @GetMapping(value = "/{batch-id}", produces = "application/json")
    public ResponseEntity<BatchResponse> handleGetBatchInfoById(@PathVariable("batch-id") long id) {
        return ResponseEntity.ok(batchService.getBatchInfoAndAveragesById(id));
    }

    @GetMapping(value = "/{batch-id}/curriculum", produces = "application/json")
    public ResponseEntity<String> getCurriculum(@PathVariable("batch-id") long batchId) {
        //TODO - authenticate token
        logger.info("GET /batches/{}/curriculum received", batchId);
        return curriculumService.getCurriculum(batchId);
    }

    @PutMapping(value = "/{batch-id}/curriculum", consumes = "application/json")
    public ResponseEntity<String> setCurriculum(@PathVariable("batch-id") long batchId,
                              @RequestBody CurriculumRequest curriculumRequest) {
        //TODO - authenticate token
        logger.info("PUT /batches/{}/curriculum received", batchId);
        return curriculumService.setCurriculumDay(batchId, curriculumRequest);
    }

    @PostMapping(value = "/{batch-id}/quizzes", consumes = "application/json")
    public ResponseEntity<String> postQuiz(@PathVariable("batch-id") long batchId,
                         @RequestBody Quiz quiz) {
        //TODO - authenticate token
        logger.info("POST /batches/{}/quizzes received", batchId);
        return quizService.setNewQuiz(batchId, quiz);
    }

    @PutMapping(value = "/{batch-id}/quizzes/{quiz-id}", consumes = "application/json")
    public ResponseEntity<String> putQuiz(@PathVariable("batch-id") long batchId,
                        @PathVariable("quiz-id") long quizId,
                        @RequestBody Quiz quiz) {
        //TODO - authenticate token
        logger.info("PUT /batches/{}/quizzes/{} received", batchId, quizId);
        quiz.setId(quizId);
        return quizService.updateQuiz(batchId, quiz);
    }

    @DeleteMapping(value = "/{batch-id}/quizzes/{quiz-id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable("batch-id") long batchId,
                           @PathVariable("quiz-id") long quizId) {
        //TODO - authenticate token
        logger.info("DELETE /batches/{}/quizzes/{} received", batchId, quizId);
        return quizService.removeQuiz(batchId, quizId);
    }

    @GetMapping(value = "/{batch-id}/topics/{topic-id}", produces = "application/json")
    public ResponseEntity<String> getTopic(@PathVariable("batch-id") long batchId,
                                           @PathVariable("topic-id") long topicId) {
        //TODO - authenticate token
        logger.info("GET /batches/{}/topics/{} received", batchId, topicId);
        return topicService.getTopic(batchId, topicId);
    }


    /**
     * Below is all CRUD controller methods for
     * path: /batch/:batchId/associates
     * @Param the long id for the batch
     * designed for trainer functionality to manipulate the associates assigned to a batch.
     *
     * --Andrew Shields
     */
    @GetMapping("/{batch-id}/associates")
    public ResponseEntity<List<Employee>> getAssociates(@PathVariable("batch-id") long batchId){
        logger.info("Accessing all associates listed under batch id: "+batchId);
        return ResponseEntity.ok().body(batchService.getAllAssociates(batchId));
    }

    @PostMapping("/{batch-id}/associates")
    public ResponseEntity<List<Employee>> postAssociates(@PathVariable("batch-id") long batchId,
                                                         @RequestHeader("Authorization")String token,
                                                         @RequestBody List<Employee> employees){
        if(jwtUtil.getRoleFromToken(token)== Role.INSTRUCTOR){
            logger.info("trainer adding employees: "+employees+" to batch: "+batchId);
            return ResponseEntity.ok().body(batchService.addAssociate(batchId, employees));
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{batch-id}/associates/{employee-id}")
    public ResponseEntity<HttpStatus> deleteAssociate(@PathVariable("batch-id") long batchId,
                                                      @RequestHeader("Authorization")String token,
                                                      @PathVariable("employee-id") long employeeId){
        if(jwtUtil.getRoleFromToken(token)== Role.INSTRUCTOR){
            logger.info("Trainer is removing employee, "+employeeId+", from batch: "+batchId);
            batchService.deleteAssociate(batchId, employeeId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
