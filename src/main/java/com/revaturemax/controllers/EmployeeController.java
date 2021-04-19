package com.revaturemax.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.models.Employee;
import com.revaturemax.services.BatchService;
import com.revaturemax.services.EmployeeService;
import com.revaturemax.util.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService empService;

    @Autowired
    BatchService batchService;

    @Autowired
    JwtUtil jwtUtil;


    private static Logger logger = LogManager.getLogger(EmployeeController.class);

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Employee> createNewEmployee(@RequestParam("name") String name,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("password") String password,
                                                      @RequestParam("role") String role) {
        logger.info("POST /employees received");
        return ResponseEntity.ok().body(empService.createNewEmployee(name, email, password, role));
    }




    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
        logger.info("Deleting an employee with id: {}", id);
        empService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return null;
    }
    
        /*
        TODO:
            -Continue Employee Endpoints
            -Access other models?
         */



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

}