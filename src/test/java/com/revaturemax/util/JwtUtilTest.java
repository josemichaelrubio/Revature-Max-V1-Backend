package com.revaturemax.util;

import com.revaturemax.model.Employee;
import com.revaturemax.model.Role;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;



class JwtUtilTest {

    private final Employee emp = new Employee(5, Role.INSTRUCTOR, "trainerTest", "trainerTest@revature.net");

    private final JwtUtil jwtUtil = new JwtUtil();

    private final Logger logger = LoggerFactory.getLogger(JwtUtilTest.class);

    @Test
    void jwtGeneratorTest(){
        String token = jwtUtil.generateToken(emp);
        logger.info("Generating a test token");
        logger.info(token);
        assertNotNull(token);

    }

    @Test
    void jwtAuthenticationTest(){
        String token = jwtUtil.generateToken(emp);
        logger.info("Generating a test token to check id authorization method");
        logger.info(token+emp.getId());
        assertTrue(jwtUtil.authorizeEmployee(token, emp.getId()));
    }

    @Test
    void jwtReturnIdTest(){
        String token = jwtUtil.generateToken(emp);
        logger.info("Generating a test token to parse and return employee ID");
        assertEquals(5, jwtUtil.getIdFromToken(token));
    }

    @Test
    void jwtReturnRoleTest(){
        String token = jwtUtil.generateToken(emp);
        logger.info("Generating a test token to parse and return employee role");
        assertEquals(Role.INSTRUCTOR, jwtUtil.getRoleFromToken(token));
    }


}
