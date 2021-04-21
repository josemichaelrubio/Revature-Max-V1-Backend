package com.revaturemax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.models.*;
import com.revaturemax.repositories.EmployeeQuizRepository;
import com.revaturemax.repositories.EmployeeRepository;
import com.revaturemax.repositories.EmployeeTopicRepository;
import com.revaturemax.repositories.PasswordRepository;
import com.revaturemax.util.Passwords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    EmployeeQuizRepository empQuizRepo;
    @Autowired
    EmployeeTopicRepository empTopicRepo;

    public ResponseEntity<String> getEmployeeInfo(Token token, long employeeId)
    {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        EmployeeResponse response = new EmployeeResponse(employee);

        List<EmployeeQuiz> quizzes = empQuizRepo.findByEmployeeEquals(employee);
        for (EmployeeQuiz quizAttempt : quizzes) {
            response.addQuizDetails(quizAttempt.getQuiz(), quizAttempt.getScore());
        }

        List<EmployeeTopic> topics =  empTopicRepo.findByEmployeeEquals(employee);
        for (EmployeeTopic et : topics) {
            response.addTopicDetails(et.getTopic(), et.getCompetency());
        }

        try {
            SimpleFilterProvider filter = new SimpleFilterProvider();
            filter.addFilter("Quiz", SimpleBeanPropertyFilter.serializeAllExcept("Topic"));
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
        } catch (DataIntegrityViolationException e) {
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
