package com.revaturemax.services;

import com.revaturemax.dto.EmployeeQuizResponse;
import com.revaturemax.dto.EmployeeResponse;
import com.revaturemax.dto.EmployeeTopicResponse;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public EmployeeResponse getEmployeeInfo(long id, EmployeeResponse employeeResponse){
        Employee emp = empRepo.getOne(id);

        employeeResponse.setName(emp.getName());

        employeeResponse.setRole(emp.getRole());

        return employeeResponse;
    }

    @Transactional
    public Employee createNewEmployee(String name, String email, String password, String role) {
        Role empRole;

        if(role.equals("INSTRUCTOR")){
            empRole = Role.INSTRUCTOR;
        } else {
            empRole = Role.ASSOCIATE;
        }

        //validate params
        Employee employee = new Employee(empRole, name, email);
        byte[] salt = Passwords.getNewPasswordSalt();
        byte[] hash = Passwords.getPasswordHash(password, salt);
        employee = empRepo.save(employee);
        passwordRepository.save(new Password(employee, salt, hash));
        return employee;
    }


    public void deleteEmployee(long id) {
        empRepo.deleteById(id);
    }

//    public void updateEmployee(Employee employee){return employeeRepository.save(employee);}

    public List<EmployeeQuizResponse> getQuizzesById(long id){
        Employee emp = empRepo.getOne(id);
        List<EmployeeQuiz> quizzes = empQuizRepo.findByEmployeeEquals(emp);
        List<EmployeeQuizResponse> quizResponses = new ArrayList<>();
        for(EmployeeQuiz q : quizzes){
            quizResponses.add(new EmployeeQuizResponse(q.getId().getQuizId(), q.getScore()));
        }
        return quizResponses;
    }

    public List<EmployeeTopicResponse> getTopicsById(long id){
        Employee emp = empRepo.getOne(id);
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
