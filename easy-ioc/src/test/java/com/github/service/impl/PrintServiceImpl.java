package com.github.service.impl;

import com.github.message.EventListener;
import com.github.message.Message;
import com.github.service.HelloWorldService;
import com.github.service.PrintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author plum-wine
 * @date 2020/04/12 11:49
 * *****************
 * function:
 */
public class PrintServiceImpl implements PrintService {

    private HelloWorldService helloWorldService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void print(String content) {
        LOGGER.info("hello obj : {}", helloWorldService);
        LOGGER.info("print content : {}", content);
    }

    @EventListener
    public void handleMessage(Message message) {
        LOGGER.info("Handle message'type is Message msg is: " + message.getMsg());
    }

}
