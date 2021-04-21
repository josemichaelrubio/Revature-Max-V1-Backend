package com.revaturemax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revaturemax.dto.LoginResponse;
import com.revaturemax.models.Batch;
import com.revaturemax.models.Employee;
import com.revaturemax.models.Password;
import com.revaturemax.models.Role;
import com.revaturemax.repositories.BatchRepository;
import com.revaturemax.repositories.EmployeeRepository;
import com.revaturemax.repositories.PasswordRepository;
import com.revaturemax.util.Passwords;
import com.revaturemax.util.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private BatchRepository batchRepository;

    public ResponseEntity<String> authenticate(String email, String passwordPlaintext) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            Password password = passwordRepository.findByEmployee(employee);
            if (password == null) {
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            byte[] hash = Passwords.getPasswordHash(passwordPlaintext, password.getSalt());
            if (Arrays.equals(hash, password.getHash())) {
                return generateAuthenticatedResponse(employee);
            }
        }
        return new ResponseEntity<String>("Invalid credentials provided", HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<String> generateAuthenticatedResponse(Employee employee) {
        String tokenString = Tokens.generateTokenString(employee);
        Long userBatchId = null;
        if (employee.getRole().equals(Role.ASSOCIATE)) {
            Batch batch = batchRepository.getBatchForAssociate(employee);
            if (batch != null) userBatchId = batch.getId();
        } else if (employee.getRole().equals(Role.INSTRUCTOR)) {
            /* Currently just returning the batchId of the most recently created batch... */
            List<Batch> batches = batchRepository.getBatchesForInstructor(employee);
            if (!batches.isEmpty()) {
                batches.sort(Comparator.comparing(Batch::getId).reversed());
                userBatchId = batches.get(0).getId();
            }
        }
        LoginResponse response = new LoginResponse(tokenString, employee, userBatchId);
        try {
            return new ResponseEntity<String>(objectMapper.writer().writeValueAsString(response), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
