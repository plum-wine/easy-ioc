package com.github.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author yihua.huang@dianping.com
 */
@Data
@NoArgsConstructor
public class HelloWorldServiceImpl implements HelloWorldService {

    private String text;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void helloWorld() {
        LOGGER.info("text:{}", text);
        LOGGER.info("hello world");
    }

}
