package com.github.context;

import com.github.context.impl.ClassPathXmlApplicationContext;
import com.github.service.HelloWorldService;
import org.junit.Test;

/**
 * @author plum-wine
 */
public class ApplicationContextTest {

    @Test
    public void test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        applicationContext.publishEvent("test");
        helloWorldService.helloWorld();
    }

}
