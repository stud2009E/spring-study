package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class FileEventLogger extends AbstractLogger{

    @Value("${events.file:target/events_log.txt}")
    private String fileName;

    protected File file;

    public FileEventLogger() {
    }

    public FileEventLogger(String fileName){
        this.fileName = fileName;
    }

    @PostConstruct
    private void init() throws IOException {
        file = new File(this.fileName);

        if (file.exists() && !file.canWrite()){
            throw new IllegalArgumentException("Can't write to file " + fileName);
        }else if(!file.exists()){
            file.createNewFile();
        }
    }

    @Override
    public void logEvent(Event event){
        try {
            FileUtils.writeStringToFile(file, '\n' + event.toString(), "UTF8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Value("File logger")
    public void setName(String name){
        this.name = name;
    }
}
