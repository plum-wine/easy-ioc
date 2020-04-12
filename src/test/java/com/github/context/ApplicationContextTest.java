package com.github.context;

import com.github.message.Message;
import com.github.service.HelloWorldService;
import org.junit.Test;

/**
 * @author plum-wine
 */
public class ApplicationContextTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        applicationContext.publishEvent("test");
        applicationContext.publishEvent(new Message("test"));
        helloWorldService.helloWorld();
    }

}
