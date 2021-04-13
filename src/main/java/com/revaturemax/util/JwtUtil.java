package com.revaturemax.util;

import com.revaturemax.model.Employee;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;


/**
 * This class configures, generates, and parses JWT tokens for authentication controller
 */

@Component
public class JwtUtil {

    private final Key key;
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // this method generates a JWT token with ID and Role stored within.
    // token is returned to the client and stored in session storage.
    public String generateToken(Employee emp) {
        String token = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setPayload(emp.getRole().toString())
                .setSubject(emp.getId().toString())
                .signWith(key).compact();
        logger.info("Token was generated and is being returned to the client");
        logger.info(token);
        return token;
    }


    // this method authenticates an employee by comparing the path parameter id to the JWT
    // returns true or false depending on the "==" conditional statement evaluation
    public boolean authorizeEmployee(String token, int id) {
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        logger.info("Employee is being authenticated by JWT");

        int subId = Integer.parseInt(subject);

        return subId == id;
    }



    public JwtUtil(){
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }


}
