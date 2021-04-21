package com.revaturemax.controllers;

import com.revaturemax.models.*;
import com.revaturemax.services.*;
import com.revaturemax.util.Tokens;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeController {

    private static Logger logger = LogManager.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;
    @Autowired
    QuizService quizService;
    @Autowired
    TopicService topicService;
    @Autowired
    NotesService notesService;

    @GetMapping(path = "/{employee-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEmployeeInfo(@PathVariable("employee-id") long employeeId,
                                                  @RequestHeader("Authorization") String authorization)
    {
        Token token = Tokens.parseToken(authorization);
        if (token == null) return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        logger.info("GET /employees/{} received", employeeId);
        return employeeService.getEmployeeInfo(token, employeeId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createNewEmployee(@RequestParam("name") String name,
                                         @RequestParam("email") String email,
                                         @RequestParam("password") String password)
    {
        logger.info("POST /employees received");
        return employeeService.createNewEmployee(name, email, password);
    }

    /*@DeleteMapping(path = "/{employee-id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employee-id") long employeeId,
                                          @RequestHeader("Authorization") String authorization)
    {
        Token token = Tokens.parseToken(authorization);
        if (token == null) return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        logger.info("Deleting an employee with id: {}", employeeId);
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }*/

    /*@PutMapping(path = "/{employee-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateEmployee(@PathVariable("employee-id") long employeeId,
                                          @RequestBody Employee employee,
                                          @RequestHeader("Authorization") String authorization)
    {
        Token token = Tokens.parseToken(authorization);
        if (token == null) return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        //TODO
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }*/

    @PutMapping(path = "/{employee-id}/quizzes/{quiz-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setEmployeeQuiz(@PathVariable("employee-id") long employeeId,
                                           @PathVariable("quiz-id") long quizId,
                                           @RequestBody EmployeeQuiz employeeQuiz,
                                           @RequestHeader("Authorization") String authorization)
    {
        Token token = Tokens.parseToken(authorization);
        if (token == null) return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        logger.info("PUT /employees/{}/quizzes/{} received", employeeId, quizId);
        quizService.setEmployeeQuiz(token, employeeId, quizId, employeeQuiz);
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/{employee-id}/topics/{topic-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setEmployeeTopic(@PathVariable("employee-id") long employeeId,
                                          @PathVariable("topic-id") long topicId,
                                          @RequestBody EmployeeTopic employeeTopic,
                                          @RequestHeader("Authorization") String authorization)
    {
        Token token = Tokens.parseToken(authorization);
        if (token == null) return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        logger.info("PUT /employees/{}/topics/{} received", employeeId, topicId);
        return topicService.setEmployeeTopic(token, employeeId, topicId, employeeTopic);
    }

    @PutMapping(path = "/{employee-id}/notes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setNotes(@PathVariable("employee-id") long employeeId,
                                           @RequestBody Notes notes,
                                           @RequestHeader("Authorization") String authorization)
    {
        Token token = Tokens.parseToken(authorization);
        if (token == null) return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        logger.info("PUT /employees/{}/notes received", employeeId);
        return notesService.setNotes(token, employeeId, notes);
    }

}
