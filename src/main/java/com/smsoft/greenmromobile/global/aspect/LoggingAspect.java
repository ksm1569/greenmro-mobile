package com.smsoft.greenmromobile.global.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.smsoft.greenmromobile..controller..*)")
    public void controllerLogging() {}

    @Around("controllerLogging()")
    public Object logMethodAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logger.debug("Method: {}-{}() ",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());

        logger.debug("result: {} | Duration: {} ms",
                result.toString().split(",")[0],
                (endTime - startTime));

        return result;
    }
}