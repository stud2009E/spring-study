package com.yet.spring.core.loggers;

public abstract class AbstractLogger implements EventLogger{

    protected String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
