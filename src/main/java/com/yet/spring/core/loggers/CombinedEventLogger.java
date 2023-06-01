package com.yet.spring.core.loggers;

import java.util.List;

public class CombinedEventLogger implements EventLogger{

    private List<EventLogger> loggers;

    public CombinedEventLogger(List<EventLogger> loggers){
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        loggers.forEach(logger -> logger.logEvent(event));
    }
}
