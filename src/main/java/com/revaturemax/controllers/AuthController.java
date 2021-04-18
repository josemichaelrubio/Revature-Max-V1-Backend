package com.revaturemax.controllers;

import com.revaturemax.models.Employee;
import com.revaturemax.models.Password;
import com.revaturemax.services.EmployeeService;
import com.revaturemax.util.JwtUtil;
import com.revaturemax.util.Passwords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> login(LoginRequest loginRequest){
        logger.info("Checking for authorization");
        Employee emp = employeeService.getByEmail(loginRequest.getEmail());
        logger.info("Employee: "+emp.getName()+" retrieved from database.");
        logger.info("Attempting to retrieve password from DB");
        if(employeeService.checkPassByEmployee(emp, loginRequest.getPassword())){


            String token = jwtUtil.generateToken(emp);

            return ResponseEntity.ok()
                    .header("Authorization", token)
                    .body("success!");
        }

        return new ResponseEntity<>("Incorrect credentials", HttpStatus.UNAUTHORIZED);
    }


    private static class LoginRequest{
        private String email;
        private String password;

        public void loginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public void loginRequest() {
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
