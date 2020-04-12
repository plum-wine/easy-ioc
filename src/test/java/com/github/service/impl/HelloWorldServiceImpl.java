package com.github.service.impl;

import com.github.annotation.AutoWired;
import com.github.message.ApplicationEventPublisher;
import com.github.message.EventListener;
import com.github.message.Message;
import com.github.service.HelloWorldService;
import com.github.service.PrintService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author plum-wine
 */
@Data
@NoArgsConstructor
public class HelloWorldServiceImpl implements HelloWorldService {

    private String text;

    private PrintService printService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AutoWired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void helloWorld() {
        printService.print(text);
    }

    @EventListener
    public void handleMessage(String msg) {
        LOGGER.info("Handle type String's message is:" + msg);
        applicationEventPublisher.publishEvent(new Message("through autowired publisher publish this message"));
    }

}
