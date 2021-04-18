package com.revaturemax.controllers;

import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.dto.NewEmployee;
import com.revaturemax.models.Employee;
import com.revaturemax.models.Role;
import com.revaturemax.services.BatchService;
import com.revaturemax.services.EmployeeService;
import com.revaturemax.util.JwtUtil;
import com.revaturemax.util.Passwords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Employee> createNewEmployee(NewEmployee emp){
        logger.info("Adding a new employee: {}", emp);

        Employee employee = empService.add(emp);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
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

            Employee emp = empService.getById(id);

            employeeResponse.setName(emp.getName());

            employeeResponse.setRole(emp.getRole());

            long batchId = batchService.getByAssociate(emp);

            if(batchId>0){
                employeeResponse.setBatchId(batchId);
            }

            List<EmployeeQuizResponse> quizzes = empService.getQuizzesById(emp);

            if(quizzes!=null){
                employeeResponse.setQuizzes(quizzes);
            }

            List<EmployeeTopicResponse> topics = empService.getTopicsById(emp);
            if(topics!=null){
                employeeResponse.setTopics(topics);
            }

            return ResponseEntity.ok().body(employeeResponse);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

}