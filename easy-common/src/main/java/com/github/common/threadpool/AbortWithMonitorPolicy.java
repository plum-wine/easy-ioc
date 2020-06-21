package com.github.common.threadpool;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hangs.zhang
 * @date 2020/6/1 下午7:12
 * *********************
 * function:
 */
@Setter
public class AbortWithMonitorPolicy extends ThreadPoolExecutor.AbortPolicy {

    private String threadPoolName;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String MONITOR_NAME = "%s.Reject";

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        LOGGER.info(String.format(MONITOR_NAME, threadPoolName));
        super.rejectedExecution(r, e);
    }

}
