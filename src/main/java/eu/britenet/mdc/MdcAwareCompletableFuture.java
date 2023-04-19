package eu.britenet.mdc;

import org.slf4j.MDC;

import java.util.concurrent.CompletableFuture;

public class MdcAwareCompletableFuture<T> extends CompletableFuture<T> {
    public static Runnable withMdc(Runnable runnable) {
        var mdcMap = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(mdcMap);
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
