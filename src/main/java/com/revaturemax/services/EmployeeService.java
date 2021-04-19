package com.revaturemax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.models.*;
import com.revaturemax.repositories.EmployeeQuizRepository;
import com.revaturemax.repositories.EmployeeRepository;
import com.revaturemax.repositories.EmployeeTopicRepository;
import com.revaturemax.repositories.PasswordRepository;
import com.revaturemax.util.Passwords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EmployeeRepository empRepo;
    @Autowired
    PasswordRepository passwordRepository;
    @Autowired
    EmployeeQuizRepository empQuizRepo;
    @Autowired
    EmployeeTopicRepository empTopicRepo;


    public Employee getById(long id){
        return empRepo.getOne(id);
    }

    @Transactional
    public ResponseEntity<String> createNewEmployee(String name, String email, String password) {
        //validate params
        Employee employee = new Employee(Role.ASSOCIATE, name, email);
        byte[] salt = Passwords.getNewPasswordSalt();
        byte[] hash = Passwords.getPasswordHash(password, salt);
        employee = empRepo.save(employee);
        passwordRepository.save(new Password(employee, salt, hash));
        try {
            return new ResponseEntity<String>(objectMapper.writer().writeValueAsString(employee), HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteEmployee(long id) {
        empRepo.deleteById(id);
    }

//    public void updateEmployee(Employee employee){return employeeRepository.save(employee);}

    public List<EmployeeQuizResponse> getQuizzesById(Employee emp){
        List<EmployeeQuiz> quizzes = empQuizRepo.findByEmployeeEquals(emp);
        List<EmployeeQuizResponse> quizResponses = new ArrayList<>();
        for(EmployeeQuiz q : quizzes){
            quizResponses.add(new EmployeeQuizResponse(q.getId().getQuizId(), q.getScore()));
        }
        return quizResponses;
    }

    public List<EmployeeTopicResponse> getTopicsById(Employee emp){
        List<EmployeeTopic> topics =  empTopicRepo.findByEmployeeEquals(emp);
        List<EmployeeTopicResponse> topicResponses = new ArrayList<>();
        for(EmployeeTopic t : topics){
            topicResponses.add(new EmployeeTopicResponse(t.getTopic().getName(), t.getTopic().getTag().getName(), t.getCompetency()));
        }
        return topicResponses;
    }
//
//    public EmployeeTopic update(EmployeeTopicId id, EmployeeTopicResponse empTopic){
//        EmployeeTopic updateTopic = empTopicRepo.getOne(id);
//        updateTopic.setCompetency(empTopic.getCompetency());
//        updateTopic.setFavNotes(empTopic.;
//        empTopicRepo.save(updateTopic);
//        return updateTopic;
//    }

}
