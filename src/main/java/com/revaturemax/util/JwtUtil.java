package com.revaturemax.util;

import com.revaturemax.models.Employee;
import com.revaturemax.models.Role;
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
                .setId(emp.getId().toString())
                .setSubject(emp.getRole().toString())
                .signWith(key).compact();
        logger.info("Token was generated and is being returned to the client");
        logger.info(token);
        return token;
    }


    // this method authenticates an employee by comparing the path parameter id to the JWT
    // returns true or false depending on the "==" conditional statement evaluation
    public boolean authorizeEmployee(String token, long id) {
        String jwtId = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
        logger.info("Employee is being authenticated by JWT");

        if(jwtId.matches("^\\d+$")){
            long check = Long.parseLong(jwtId);

            logger.info(id+"?==?"+check);

            return check==id;
        }
        return false;

    }

    // this method parses the JWT token and returns the long employee ID
    public long getIdFromToken(String token){
        String jwtId = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
        logger.info("Retrieving employee ID from JWT");
        return Long.parseLong(jwtId);

    }

    // this method parses the JWT token and returns the long employee ID
    public Role getRoleFromToken(String token){
        String jwtRole = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        logger.info("Retrieving employee role from JWT");
        switch (jwtRole){
            case "INSTRUCTOR":
                return Role.INSTRUCTOR;
            case "ASSOCIATE":
                return Role.ASSOCIATE;
            default:
                return null;
        }

    }



    public JwtUtil(){
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }


}
