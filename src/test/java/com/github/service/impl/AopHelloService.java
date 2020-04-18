package com.github.service.impl;

import com.github.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/4/14 下午8:00
 * *********************
 * function:
 */
public class AopHelloService implements HelloWorldService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void helloWorld() {
        LOGGER.info("test hello world");
    }
}
