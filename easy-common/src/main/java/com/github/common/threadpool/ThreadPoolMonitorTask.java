package com.github.common.threadpool;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 2020/6/1 下午4:16
 * *********************
 * function:
 */
public class ThreadPoolMonitorTask implements Runnable {

    private static final String MONITOR_PREFIX = "ThreadPool_";

    private static final Map<String, ThreadPoolExecutorHolder> TO_MONITORED_EXECUTOR_MAP = Maps.newConcurrentMap();

    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PostConstruct
    public void init() {
        EXECUTOR.scheduleAtFixedRate(this, 0, 30, TimeUnit.SECONDS);
    }

    public static void addExecutor(ThreadPoolExecutorHolder tamiasThreadPoolExecutor) {
        TO_MONITORED_EXECUTOR_MAP.put(tamiasThreadPoolExecutor.getName(), tamiasThreadPoolExecutor);
    }

    private Map<String, Integer> execute() {
        Map<String, Integer> ret = Maps.newLinkedHashMap();
        try {
            for (Map.Entry<String, ThreadPoolExecutorHolder> entry : TO_MONITORED_EXECUTOR_MAP.entrySet()) {
                ThreadPoolExecutorHolder tp = entry.getValue();
                String prefix = MONITOR_PREFIX + entry.getKey() + "_";
                ret.put(prefix + "activeCount", tp.getActiveCount());
                ret.put(prefix + "poolSize", tp.getPoolSize());
                ret.put(prefix + "currentQueueSize", tp.getQueueSize());
                ret.put(prefix + "maximumPoolSize", tp.getMaximumPoolSize());
                ret.put(prefix + "corePoolSize", tp.getCorePoolSize());
                // 活跃度
                ret.put(prefix + "active", (int) ((double) tp.getActiveCount() / tp.getMaximumPoolSize() * 100));
            }
        } catch (Exception e) {
            LOGGER.error("record thread pool exception", e);
            ret.put(MONITOR_PREFIX + "ERROR", 1);
        }
        return ret;
    }

    @Override
    public void run() {
        Map<String, Integer> result = execute();
        for (Map.Entry<String, Integer> entry : result.entrySet()) {

        }
    }

    @PreDestroy
    public void destroy() {
        EXECUTOR.shutdownNow();
        EXECUTOR.shutdown();
    }
}
