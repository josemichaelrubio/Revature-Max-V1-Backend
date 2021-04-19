package com.revaturemax.controllers;

import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.models.Employee;
import com.revaturemax.services.BatchService;
import com.revaturemax.services.EmployeeService;
import com.revaturemax.util.JwtUtil;
import com.revaturemax.models.EmployeeQuiz;
import com.revaturemax.models.EmployeeTopic;
import com.revaturemax.models.Notes;
import com.revaturemax.services.*;
import com.revaturemax.services.NotesService;
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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService empService;

    @Autowired
    BatchService batchService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TopicService topicService;

    @Autowired
    QuizService quizService;

    private static Logger logger = LogManager.getLogger(EmployeeController.class);





    @Autowired
    NotesService notesService;


    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> createNewEmployee(@RequestParam("name") String name,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("password") String password) {
        logger.info("POST /employees received");
        return empService.createNewEmployee(name, email, password);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeInfo(@PathVariable long id, @RequestHeader("Authorization") String token){

        if(jwtUtil.authorizeEmployee(token, id)){
            EmployeeResponse employeeResponse = new EmployeeResponse();

            EmployeeResponse emp = empService.getEmployeeInfo(id, employeeResponse);

            long batchId = batchService.getByAssociate(id);

            if(batchId>0){
                employeeResponse.setBatchId(batchId);
            }

            List<EmployeeQuizResponse> quizzes = empService.getQuizzesById(id);

            if(quizzes!=null){
                employeeResponse.setQuizzes(quizzes);
            }

            List<EmployeeTopicResponse> topics = empService.getTopicsById(id);
            if(topics!=null){
                employeeResponse.setTopics(topics);
            }

            return ResponseEntity.ok().body(employeeResponse);
        }


        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
        logger.info("Deleting an employee with id: {}", id);
        empService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        /*return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setEmail(newEmployee.getEmail());
                    logger.info("Updating employee details");
                    return repository.save(employee);

                })
                .orElse(null);*/
        return null;
    }

    @PutMapping(value = "/{employee-id}/quizzes/{quiz-id}")
    public ResponseEntity<HttpStatus> setEmployeeQuiz(@PathVariable("employeeId") long employeeId,
                                           @PathVariable("quiz-id") long quizId,
                                           @RequestBody EmployeeQuiz employeeQuiz) {
        //authorize JWT
        logger.info("PUT /employees/{}/quizzes/{} received", employeeId, quizId);
        quizService.setEmployeeQuiz(employeeId, quizId, employeeQuiz);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/{employee-id}/topics/{topic-id}")
    public ResponseEntity<HttpStatus> setEmployeeTopic(@PathVariable("employeeId") long employeeId,
                                          @PathVariable("topic-id") long topicId,
                                          @RequestBody EmployeeTopic employeeTopic) {
        //authorize JWT
        logger.info("PUT /employees/{}/topics/{} received", employeeId, topicId);
        topicService.setEmployeeTopic(employeeId, topicId, employeeTopic);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/{employee-id}/notes")
    public ResponseEntity<String> setNotes(@PathVariable("employeeId") long employeeId,
                                          @RequestBody Notes notes) {
        //authorize JWT
        logger.info("PUT /employees/{}/notes received", employeeId);
        return notesService.setNotes(employeeId, notes);
    }

}