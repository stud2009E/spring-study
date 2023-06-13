package com.yet.spring.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods(){

    }

    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint jp){
        System.out.println("BEFORE: " + jp.getTarget().getClass().getSimpleName() + " "
                + jp.getSignature().getName());
    }

    @AfterReturning(pointcut = "allLogEventMethods()", returning = "retValue")
    public void logAfter(Object retValue){
        System.out.println("AFTER_RET: " + retValue);
    }

    @AfterThrowing(pointcut = "allLogEventMethods()", throwing = "ex")
    public void logAfterThrow(Throwable ex){
        System.out.println("AFTER_THR " + ex);
    }
}