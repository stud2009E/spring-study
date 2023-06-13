package com.yet.spring.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class StatisticsAspect {

    private Map<Class<?>, Integer> counter = new HashMap<>();

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethod(){

    }

    @AfterReturning("allLogEventMethod()")
    public void count(JoinPoint jp){
        Class<?> clazz = jp.getTarget().getClass();
        if(!counter.containsKey(clazz)){
            counter.put(clazz, 0);
        }

        counter.put(clazz, counter.get(clazz) + 1);
    }

    @AfterReturning("execution(* com.yet.spring.core.App.logEvents(..))")
    public void outPutLoggingCounter(){
        System.out.println("Logger statistics:");

        counter.forEach((aClass, i) -> {
            System.out.println(aClass.getSimpleName() + ": " + i);
        });
    }

}
