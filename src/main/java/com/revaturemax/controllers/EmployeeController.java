package com.revaturemax.controllers;

import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.model.Employee;
import com.revaturemax.model.EmployeeTopic;
import com.revaturemax.services.BatchService;
import com.revaturemax.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService empService;

    @Autowired
    BatchService batchService;

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
