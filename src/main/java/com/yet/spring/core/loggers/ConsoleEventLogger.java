package com.yet.spring.core.loggers;

public class ConsoleEventLogger implements EventLogger{

    @Override
    public void logEvent(Event event){
        System.out.println(event);
    }
}
