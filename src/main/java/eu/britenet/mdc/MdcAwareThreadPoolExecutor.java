package eu.britenet.mdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MdcAwareThreadPoolExecutor extends ThreadPoolExecutor {

    public MdcAwareThreadPoolExecutor(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue
    ) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        var mdcMap = MDC.getCopyOfContextMap();
        super.execute(() -> {
            MDC.setContextMap(mdcMap);
            command.run();
        });
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        MDC.clear();
        super.afterExecute(r, t);
    }
}
