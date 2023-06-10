package com.yet.spring.core.spring;

import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.EventLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

@Configuration
public class LoggerConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Resource(name = "fileEventLogger")
    private EventLogger fileEventLogger;

    @Resource(name = "consoleEventLogger")
    private EventLogger consoleEventLogger;

    @Resource(name = "combinedEventLogger")
    private EventLogger combinedEventLogger;

    @Bean
    public Collection<EventLogger> combinedLoggers(){
        Collection<EventLogger> loggers = new ArrayList<>();

        loggers.add(consoleEventLogger);
        loggers.add(fileEventLogger);

        return  loggers;
    }

    @Bean
    public Map<EventType, EventLogger> loggerMap(){
        Map<EventType, EventLogger> map = new EnumMap(EventType.class);
        map.put(EventType.INFO, consoleEventLogger);
        map.put(EventType.ERROR, combinedEventLogger);

        return map;
    }
}
