package com.revaturemax.aspects;

import com.revaturemax.model.Role;
import com.revaturemax.util.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthAspect {

    @Autowired
    JwtUtil jwtUtil;

    @Around("")
    public void authorizeInstructorRequest(ProceedingJoinPoint jp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if(token!=null){
            Role role = jwtUtil.getRoleFromToken(token);
            if(role == Role.INSTRUCTOR){
                jp.proceed();
            }
        }
    }
}
