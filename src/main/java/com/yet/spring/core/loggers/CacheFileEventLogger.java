package com.yet.spring.core.loggers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger{

    private final int cacheSize;
    private final List<Event> cache = new ArrayList<>();

    public CacheFileEventLogger(String fileName, int cacheSize) throws IOException {
        super(fileName);

        this.cacheSize = cacheSize;
    }

    private void writeEventsFromCache(){
        cache.forEach(super::logEvent);
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);

        if (cache.size() == cacheSize){
            writeEventsFromCache();
            cache.clear();
        }
    }

    public void destroy() {
        if (!cache.isEmpty()){
            writeEventsFromCache();
        }
    }
}
