package com.revaturemax.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username")String username,
                                        @RequestParam("password")String password) {
        logger.info("Checking for authorization");
        if(username.equals("associate")){
            if(password.equals("123")){
                return ResponseEntity.ok()
                        .header("Authorization", "asso-auth-token")
                        .body("success!");
            }
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Username not recognized", HttpStatus.UNAUTHORIZED);
    }

    /*
    TODO:
        - Link DB/JWT Credentials, and replace hard coded credentials
     */
}
