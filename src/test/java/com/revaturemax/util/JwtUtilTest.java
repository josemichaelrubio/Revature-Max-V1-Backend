package com.revaturemax.util;

import com.revaturemax.model.Employee;
import com.revaturemax.model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class JwtUtilTest {

    private final Employee emp = new Employee(1, Role.INSTRUCTOR, "trainerTest", "trainerTest@revature.net");

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void jwtGeneratorTest(){
        String token = jwtUtil.generateToken(emp);
        System.out.println("Generating a test token");
        System.out.println(token);
        assertNotNull(token);

    }

    @Test
    void jwtAuthenticationTest(){
        String token = jwtUtil.generateToken(emp);
        System.out.println("Generating a test token to check id authorization method");
        System.out.println(token+emp.getId());
        assertTrue(jwtUtil.authorizeEmployee(token, emp.getId()));
    }


}
