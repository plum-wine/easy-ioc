package com.github.common.threadpool;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 2020/6/1 下午7:35
 * *********************
 * function:
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    private static final ThreadLocal<Stopwatch> STOPWATCH_THREAD_LOCAL = new ThreadLocal<>();

    private final NamedThreadFactory namedThreadFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, NamedThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.namedThreadFactory = threadFactory;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        STOPWATCH_THREAD_LOCAL.set(stopwatch);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // monitor
        LOGGER.info(namedThreadFactory.getPrefix());
        LOGGER.info(namedThreadFactory.getPrefix(), STOPWATCH_THREAD_LOCAL.get().elapsed(TimeUnit.MILLISECONDS));
    }
}
