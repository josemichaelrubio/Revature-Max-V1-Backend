package com.revaturemax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.models.*;
import com.revaturemax.repositories.*;
import com.revaturemax.util.Passwords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordRepository passwordRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    EmployeeQuizRepository employeeQuizRepository;
    @Autowired
    EmployeeTopicRepository employeeTopicRepository;

    public ResponseEntity<String> getEmployeeInfo(Token token, long employeeId)
    {
        if (token.getEmployeeId() != employeeId) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        EmployeeResponse response = new EmployeeResponse(employee);
        Batch batch = batchRepository.getBatchForAssociate(employee);
        if (batch == null) return writeEmployeeResponse(response);

        List<Quiz> quizzes = quizRepository.findQuizzesByBatch(batch.getId());
        for (Quiz quiz : quizzes) response.addQuiz(quiz);
        List<EmployeeQuiz> quizScores = employeeQuizRepository.findByEmployeeEquals(employee);
        for (EmployeeQuiz quizScore : quizScores) response.addQuizScore(quizScore.getQuiz(), quizScore.getScore());

        List<EmployeeTopic> topics =  employeeTopicRepository.findByEmployeeEquals(employee);
        for (EmployeeTopic et : topics) response.addTopicCompetency(et.getTopic(), et.getCompetency());

        return writeEmployeeResponse(response);
    }

    private ResponseEntity<String> writeEmployeeResponse(EmployeeResponse response) {
        try {
            SimpleFilterProvider filter = new SimpleFilterProvider();
            filter.addFilter("Quiz", SimpleBeanPropertyFilter.serializeAll());
            filter.addFilter("Topic", SimpleBeanPropertyFilter.serializeAll());
            return new ResponseEntity<String>(objectMapper.writer(filter).writeValueAsString(response),
                    HttpStatus.OK);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<String> createNewEmployee(String name, String email, String password)
    {
        if (!validName(name)) return new ResponseEntity<String>("Bad name", HttpStatus.BAD_REQUEST);
        if (!validEmail(email)) return new ResponseEntity<String>("Bad email", HttpStatus.BAD_REQUEST);
        if (!validPassword(password)) return new ResponseEntity<String>("Bad password", HttpStatus.BAD_REQUEST);

        Employee employee = new Employee(Role.ASSOCIATE, name, email);
        try {
            employee = employeeRepository.save(employee);
        } catch (UnexpectedRollbackException e) {
            return new ResponseEntity<String>("The provided email is already taken", HttpStatus.CONFLICT);
        }

        byte[] salt = Passwords.getNewPasswordSalt();
        byte[] hash = Passwords.getPasswordHash(password, salt);
        passwordRepository.save(new Password(employee, salt, hash));
        try {
            return new ResponseEntity<String>(objectMapper.writer().writeValueAsString(employee), HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }*/

    private boolean validName(String name) {
        return name != null && !name.equals("") && name.length() < 256;
    }

    private boolean validEmail(String email) {
        if (email == null) return false;
        return email.matches("^\\S+@\\S+\\.\\S+$") && email.length() < 256;
    }

    private boolean validPassword(String password) {
        return password != null && !password.equals("");
    }

}
