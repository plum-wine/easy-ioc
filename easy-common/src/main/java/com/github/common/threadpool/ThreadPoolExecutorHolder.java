package com.github.common.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author hangs.zhang
 * @date 2020/6/1 下午2:22
 * *********************
 * function:
 */
public class ThreadPoolExecutorHolder implements ExecutorService {

    private final String name;

    private final NamedThreadFactory threadFactory;

    private volatile CustomThreadPoolExecutor threadPoolExecutor;

    private final BlockingQueue<Runnable> workQueue;

    private final RejectedExecutionHandler rejectedExecutionHandler;

    private static final String KEY_FORMAT = "%s.%s";

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ThreadPoolExecutorHolder(String name, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler rejectedExecutionHandler) {
        this.name = name;
        this.workQueue = workQueue;
        this.rejectedExecutionHandler = rejectedExecutionHandler;
        this.threadFactory = new NamedThreadFactory(name);
    }

    public ThreadPoolExecutorHolder(String name, BlockingQueue<Runnable> workQueue) {
        this.name = name;
        this.workQueue = workQueue;
        this.rejectedExecutionHandler = new AbortWithMonitorPolicy();
        this.threadFactory = new NamedThreadFactory(name);
    }

    @PostConstruct
    public void init() {
        this.threadPoolExecutor = new CustomThreadPoolExecutor(getCorePoolSize(), getMaximumPoolSize(),
                getKeepAliveTime(), TimeUnit.SECONDS, this.workQueue, this.threadFactory, this.rejectedExecutionHandler);
        ThreadPoolMonitorTask.addExecutor(this);
    }

    public String getName() {
        return this.name;
    }

    public int getCorePoolSize() {
        throw new RuntimeException();
    }

    public int getMaximumPoolSize() {
        throw new RuntimeException();
    }

    public long getKeepAliveTime() {
        throw new RuntimeException();
    }

    private String getKey(ThreadPoolParam poolParam) {
        return String.format(KEY_FORMAT, name, poolParam.name());
    }

    public int getQueueSize() {
        return workQueue.size();
    }

    public int getPoolSize() {
        return threadPoolExecutor.getPoolSize();
    }

    public int getActiveCount() {
        return threadPoolExecutor.getActiveCount();
    }

    @Override
    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return threadPoolExecutor.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return threadPoolExecutor.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return threadPoolExecutor.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return threadPoolExecutor.awaitTermination(timeout, unit);
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return this.threadPoolExecutor.submit(runnable);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return this.threadPoolExecutor.submit(callable);
    }

    @Override
    public <T> Future<T> submit(Runnable runnable, T result) {
        return this.threadPoolExecutor.submit(runnable, result);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.threadPoolExecutor.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return this.threadPoolExecutor.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return this.threadPoolExecutor.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.threadPoolExecutor.invokeAny(tasks, timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        this.threadPoolExecutor.execute(command);
    }

}
