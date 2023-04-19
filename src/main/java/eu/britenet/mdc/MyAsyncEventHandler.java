package eu.britenet.mdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static eu.britenet.mdc.MdcAwareCompletableFuture.withMdc;

public class MyAsyncEventHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final List<EventParamsProcessor> eventParamsProcessors;
    private final ExecutorService executorService;

    public MyAsyncEventHandler(List<EventParamsProcessor> eventParamsProcessors, ExecutorService executorService) {
        this.eventParamsProcessors = eventParamsProcessors;
        this.executorService = executorService;
    }

    public void handle(MyEvent event) {
        MDC.put("event.id", event.getId());
        log.info("Handle event from: {}", event.getSender());

//        eventParamsProcessors.stream()
//                .map(processor -> MdcAwareCompletableFuture.runAsync(
//                        withMdc(() -> processor.process(event.getParams())),
//                        executorService
//                )
//                ).forEach(CompletableFuture::join);

        eventParamsProcessors.stream()
                .map(processor -> CompletableFuture.runAsync(
                        () -> processor.process(event.getParams()),
                        executorService
                )
                ).forEach(CompletableFuture::join);

        MDC.remove("event.id");
    }
}
