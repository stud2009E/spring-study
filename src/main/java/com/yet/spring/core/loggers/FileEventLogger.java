package com.yet.spring.core.loggers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger{

    private final String fileName;
    protected File file;


    public FileEventLogger(String fileName) throws IOException {
        this.fileName = fileName;

        this.init();
    }

    private void init()  {
        file = new File(this.fileName);

        if (file.exists() && !file.canWrite()){
            throw new IllegalArgumentException("Can't write to file " + fileName);
        }else if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException("Can't create file " + fileName);
            }
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
}
