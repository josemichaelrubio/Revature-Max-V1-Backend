package com.revaturemax.controllers;

import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.dto.TopicRequest;
import com.revaturemax.models.Employee;
import com.revaturemax.models.EmployeeTopic;
import com.revaturemax.models.Notes;
import com.revaturemax.models.Topic;
import com.revaturemax.repositories.EmployeeRepository;
import com.revaturemax.services.BatchService;
import com.revaturemax.services.EmployeeService;
import com.revaturemax.services.NotesService;
import com.revaturemax.services.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
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
    private TopicService eService;

    @Autowired
    NotesService notesService;



// EmployeeTopic tRepo;
// EmployeeController(EmployeeTopic tRepo){
//     this.tRepo = tRepo;
// }
 EmployeeRepository repository;
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

//    @PutMapping("{id}/topics/{topics-id}")
//    public ResponseEntity<Topic> updateTopic(@PathVariable long id, @RequestBody TopicRequest topic){
//        //authenticate instructor
//        logger.info("Instructor creating new topic");
//        return ResponseEntity.ok().body(topicService.update(id, topic));
//    }

    @PostMapping("{id}/notes")
    public ResponseEntity<Notes> createNotes(@RequestBody Notes notes){
        logger.info("Adding a new Notes: {}", notes);
        notesService.addNotes(notes);
        return new ResponseEntity<>(notes, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/notes/{id}")
    public ResponseEntity<HttpStatus> deleteNotes(@PathVariable long id){
        logger.info("Deleting an Notes with id: {}", id);
        notesService.deleteNotes(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("{id}/notes/{id}")
    public ResponseEntity<Notes> updateNotes(@RequestBody Notes newNotes, @PathVariable Long id){
        logger.info("Updating notes with id: {}", id);
        notesService.updateNotes(id);
        return ResponseEntity.ok().body(notesService.updateNotes(id,newNotes));
    }
    //no session LazyInitializationException




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