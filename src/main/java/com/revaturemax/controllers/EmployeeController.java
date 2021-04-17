package com.revaturemax.controllers;

import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.models.Employee;
import com.revaturemax.repositories.EmployeeRepository;
import com.revaturemax.services.BatchService;
import com.revaturemax.services.EmployeeService;
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

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    private static Logger logger = LogManager.getLogger(EmployeeController.class);

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Employee> createNewEmployee(@RequestBody Employee employee){
        logger.info("Adding a new employee: {}", employee);
        empService.add(employee);
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

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setEmail(newEmployee.getEmail());
                    logger.info("Updating employee details");
                    return repository.save(employee);

                })
                .orElse(null);
    }
    
        /*
        TODO:
            -Continue Employee Endpoints
            -Access other models?
         */



    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeInfo(@PathVariable long id){
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

}