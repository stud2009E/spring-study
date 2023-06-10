package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConsoleEventLogger extends AbstractLogger{

    @Override
    public void logEvent(Event event){
        System.out.println(event);
    }

    @Override
    @Value("console logger")
    public void setName(String name) {
        this.name = name;
    }
}
