package com.github.context;

import com.github.service.HelloWorldService;
import org.junit.Test;

/**
 * @author plum-wine
 */
public class ApplicationContextTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        applicationContext.publishEvent("test");
        helloWorldService.helloWorld();
    }

}
