package com.revaturemax.services;

import com.revaturemax.controllers.TopicController;
import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
import com.revaturemax.dto.NewEmployee;
import com.revaturemax.models.*;
import com.revaturemax.repositories.EmployeeQuizRepository;
import com.revaturemax.repositories.EmployeeRepository;
import com.revaturemax.repositories.EmployeeTopicRepository;
import com.revaturemax.repositories.PasswordRepository;
import com.revaturemax.util.Passwords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    @Autowired
    EmployeeRepository empRepo;

    @Autowired
    EmployeeQuizRepository empQuizRepo;

    @Autowired
    EmployeeTopicRepository empTopicRepo;

    @Autowired
    PasswordRepository passwordRepository;

    public Employee getById(long id){
        return empRepo.getOne(id);
    }

    public Employee add(NewEmployee emp) {

        if(emp.getRole().equals("INSTRUCTOR")){
            Role role = Role.INSTRUCTOR;
            Employee newTrainer = empRepo.save(new Employee(role, emp.getName(), emp.getEmail()));
            byte[] newSalt = Passwords.getNewPasswordSalt();
            byte[] newHash = Passwords.getPasswordHash(emp.getPassword(), newSalt);
            passwordRepository.save(new Password(newTrainer, newSalt, newHash));
            return newTrainer;
        } else {
            Role role = Role.ASSOCIATE;
            logger.info("New employee is an associate");
            Employee newTrainer = empRepo.save(new Employee(role, emp.getName(), emp.getEmail()));
            logger.info("Employee: "+newTrainer+" was saved in the DB");
            logger.info("Getting new password salt");
            byte[] newSalt = Passwords.getNewPasswordSalt();
            logger.info("Hashing new password");
            byte[] newHash = Passwords.getPasswordHash(emp.getPassword(), newSalt);
            logger.info("Saving new password for employee: "+newTrainer);
            passwordRepository.save(new Password(newTrainer, newSalt, newHash));
            return newTrainer;
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

    public Employee getByEmail(String email){
        return empRepo.findByEmail(email);
    }

    public boolean checkPassByEmployee(Employee emp, String pass){

        Password empPass = passwordRepository.findByEmployee(emp);

        byte[] salt = empPass.getSalt();
        byte[] hash = empPass.getHash();

        byte[] inputHash = Passwords.getPasswordHash(pass, salt);
        return Arrays.equals(hash, inputHash);
    }

}
