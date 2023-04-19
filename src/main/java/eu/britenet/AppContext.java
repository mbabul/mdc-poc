package eu.britenet;

import eu.britenet.mdc.EventParamsProcessor;
import eu.britenet.mdc.MdcAwareThreadPoolExecutor;
import eu.britenet.mdc.MyAsyncEventHandler;
import eu.britenet.mdc.MyEvent;
import eu.britenet.mdc.MyEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AppContext {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void app() {
        var executor = new MdcAwareThreadPoolExecutor(
                1,
                3,
                4,
                SECONDS,
                new LinkedBlockingQueue<>(10)
        );

        var eventHandler = new MyEventHandler(List.of(EventParamsProcessor.getSome(), EventParamsProcessor.getAnother()));
        var asyncEventHandler = new MyAsyncEventHandler(
                List.of(EventParamsProcessor.getSome(), EventParamsProcessor.getAnother()),
                executor
        );

        IntStream.rangeClosed(1, 3)
                .boxed()
                .map(i -> new MyEvent(i.toString(), "sender" + i, Map.of("templateId", i)))
                .forEach(asyncEventHandler::handle);

        executor.submit(() -> log.info("dupa"));
    }
}
