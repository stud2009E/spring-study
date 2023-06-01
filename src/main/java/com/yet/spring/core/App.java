package com.yet.spring.core;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.loggers.Event;
import com.yet.spring.core.loggers.EventLogger;
import com.yet.spring.core.loggers.EventType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {

    private final Client client;
    private final EventLogger defaultLogger;
    private final Map<EventType, EventLogger> loggerMap;

    public App(Client client, EventLogger logger, Map<EventType, EventLogger> loggerMap){
        super();
        this.client = client;
        this.defaultLogger = logger;
        this.loggerMap = loggerMap;
    }

    private void logEvent(EventType type, Event event){
        String message = event.getMsg().replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);

        EventLogger logger = loggerMap.get(type);
        if(logger == null){
            logger = defaultLogger;
        }

        logger.logEvent(event);
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        Event event1 = ctx.getBean("event", Event.class);
        event1.setMsg("Some event for user 1");
        app.logEvent(EventType.ERROR, event1);

        Event event2 = ctx.getBean("event", Event.class);
        event2.setMsg("Some event for user 2");
        app.logEvent(EventType.INFO,event2);

        Event event3 = ctx.getBean("event", Event.class);
        event3.setMsg("Some event for user 3");
        app.logEvent(null, event3);

        Event event4 = ctx.getBean("event", Event.class);
        event4.setMsg("Some event for user 4");
        app.logEvent(EventType.ERROR, event4);

        ctx.close();
    }
}