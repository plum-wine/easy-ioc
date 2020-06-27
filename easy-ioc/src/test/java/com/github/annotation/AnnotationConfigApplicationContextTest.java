package com.github.annotation;

import com.github.annotation.service.AService;
import com.github.context.impl.AnnotationConfigApplicationContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/6/24 下午8:15
 * *********************
 * function:
 */
public class AnnotationConfigApplicationContextTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testConfig() throws Exception {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("com.github.annotation.service");
        AService aService = (AService) applicationContext.getBean("aService");
        String result = aService.doMessage("hello");
        LOGGER.info("result:{}", result);
    }

}
