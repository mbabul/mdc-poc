package eu.britenet.mdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;

public class MyEventHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final List<EventParamsProcessor> eventParamsProcessors;

    public MyEventHandler(List<EventParamsProcessor> eventParamsProcessors) {
        this.eventParamsProcessors = eventParamsProcessors;
    }

    public void handle(MyEvent event) {
//        MDC.put("event.id", event.getId());
//        log.info("Handle event, from: {}", event.getSender());
//        eventParamsProcessors.forEach(processor -> processor.process(event.getParams()));

        try (MDC.MDCCloseable closable = MDC.putCloseable("event.id", event.getId())) {
            log.info("Handle event, from: {}", event.getSender());
            eventParamsProcessors.forEach(processor -> processor.process(event.getParams()));
        }
    }
}
