package com.revaturemax.controllers;

import com.revaturemax.dto.BatchResponse;
import com.revaturemax.dto.TopicResponse;
import com.revaturemax.models.Employee;
import com.revaturemax.models.EmployeeQuiz;
import com.revaturemax.models.Quiz;
import com.revaturemax.models.Role;
import com.revaturemax.services.BatchService;
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
