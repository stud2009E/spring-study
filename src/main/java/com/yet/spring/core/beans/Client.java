package com.yet.spring.core.beans;

public class Client {
    private final String id;
    private final String fullName;

    private String greeting;

    public Client(String id, String fullName){
        super();
        this.id = id;
        this.fullName = fullName;
    }

    public void setGreeting(String greeting){
        this.greeting = greeting;
    }

    public String getFullName() {
        return fullName;
    }

    public String getId() {
        return id;
    }
}
