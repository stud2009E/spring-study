package com.yet.spring.core.loggers;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Event {
    private final int id;
    private String msg;
    private final Date date;
    private final DateFormat df;


    public Event(Date date, DateFormat df){
        this.id = ThreadLocalRandom.current().nextInt(0, 99 + 1);
        this.date = date;
        this.df = df;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + df.format(date) +
                '}';
    }
}
