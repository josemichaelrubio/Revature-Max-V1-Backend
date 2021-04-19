package com.revaturemax.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
public class AuthAspect {
    private Logger logger = LogManager.getLogger(AuthAspect.class);

    @Around("within(com.revaturemax.controller.*)")
    public ResponseEntity authorizeRequest(ProceedingJoinPoint jp) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if (token != null) {
            /*
            TODO:
                -Need JWT
                -Check if the token is actually valid along with !=null; user is in the database and decrypt the JWT.
             */
            logger.info("auth token present");
            return (ResponseEntity) jp.proceed();
        } else {
            logger.warn("auth header not present");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

}
