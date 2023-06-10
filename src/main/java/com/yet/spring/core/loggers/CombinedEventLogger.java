package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;

@Component
public class CombinedEventLogger extends AbstractLogger{

    @Resource(name = "combinedLoggers")
    private Collection<EventLogger> loggers;

    @Override
    public void logEvent(Event event) {
        loggers.forEach(logger -> logger.logEvent(event));
    }

    public Collection<EventLogger> getLoggers(){
        return Collections.unmodifiableCollection(loggers);
    }

    @Value("#{'Combined ' + combinedLoggers.![name].toString()}")
    @Override
    public void setName(String name) {
        this.name = name;
    }
}
