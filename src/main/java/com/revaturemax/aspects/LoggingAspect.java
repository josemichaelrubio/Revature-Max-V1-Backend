package com.revaturemax.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class LoggingAspect {

    private static Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Before("within(com.revaturemax.*) && !within(com.revaturemax.models.*)")
    public void logMethodSignature(JoinPoint jp){
        logger.info(jp.getKind()+jp.getSignature());
    }

    @Before("within(com.revaturemax.controllers.*)")
    public void logRequest(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        logger.info(request.getMethod() + "request made to: "+request.getRequestURI());
    }

}
