package com.yet.spring.core;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.EventLogger;
import com.yet.spring.core.spring.AppConfig;
import com.yet.spring.core.spring.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class App {

    @Autowired
    private Client client;

    @Value("#{ T(com.yet.spring.core.beans.Event).isDay(8, 17) ? "
            + "cacheFileEventLogger : consoleEventLogger }")
    private EventLogger defaultLogger;

    @Resource(name = "loggerMap")
    private Map<EventType, EventLogger> loggerMap;

    @Value("#{'Hello user ' + " +
            "( systemProperties['os.name'].contains('Windows') ? " +
            "systemEnvironment['USERNAME'] : systemEnvironment['USER'] ) + " +
            "'. Default logger is ' + app.defaultLogger.name }")
    private String startupMessage;

    public App(){}

    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggerMap){
        this.client = client;
        this.defaultLogger = defaultLogger;
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
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class, LoggerConfig.class);
        ctx.scan("com.yet.spring.core");
        ctx.refresh();

        App app = (App) ctx.getBean("app");

        System.out.println(app.startupMessage);

        app.logEvents(ctx);

        Client client = ctx.getBean(Client.class);
        System.out.println("Client says: " + client.getGreeting());

        ctx.close();
    }

    public void logEvents(ApplicationContext ctx) {
        Event event1 = ctx.getBean("event", Event.class);
        event1.setMsg("Some event for user 1");
        logEvent(EventType.ERROR, event1);

        Event event2 = ctx.getBean("event", Event.class);
        event2.setMsg("Some event for user 2");
        logEvent(EventType.INFO,event2);

        Event event3 = ctx.getBean("event", Event.class);
        event3.setMsg("Some event for user 3");
        logEvent(null, event3);

        Event event4 = ctx.getBean("event", Event.class);
        event4.setMsg("Some event for user 4");
        logEvent(EventType.ERROR, event4);
    }

    public EventLogger getDefaultLogger(){
        return this.defaultLogger;
    }
}
