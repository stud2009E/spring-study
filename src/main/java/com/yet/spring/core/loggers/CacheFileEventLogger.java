package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class CacheFileEventLogger extends FileEventLogger{

    @Value("${cache.size:5}")
    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger(){}

    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
    }

    @PostConstruct
    public void initCache(){
        cache = new ArrayList<Event>(cacheSize);
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);

        if (cache.size() == cacheSize){
            writeEventsFromCache();
            cache.clear();
        }
    }

    @PreDestroy
    public void destroy() {
        if (!cache.isEmpty()){
            writeEventsFromCache();
        }
    }

    private void writeEventsFromCache(){
        cache.forEach(super::logEvent);
    }

    @Value("#{fileEventLogger.name + ' with cache'}")
    @Override
    public void setName(String name) {
        this.name = name;
    }
}
