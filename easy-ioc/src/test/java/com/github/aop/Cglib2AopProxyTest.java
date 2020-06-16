package com.github.aop;

import com.github.aop.advisor.AdvisedSupport;
import com.github.aop.proxy.CglibProxy;
import com.github.service.PrintService;
import com.github.service.impl.AopHelloService;
import com.github.context.ApplicationContext;
import com.github.context.ClassPathXmlApplicationContext;
import com.github.service.HelloWorldService;
import org.junit.Test;

public class Cglib2AopProxyTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop.xml");
        // 此处的代理只实现了一层
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();

        PrintService printService = (PrintService) applicationContext.getBean("printService");
        printService.print("text");
    }

    @Test
    public void test2() {
        HelloWorldService helloWorldService = new AopHelloService();

        // 1. 设置被代理对象(JoinPoint)
        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(helloWorldService, AopHelloService.class, HelloWorldService.class);
        advisedSupport.setTargetSource(targetSource);

        // 2. 设置拦截器(Advice)
        TimerInterceptor timerInterceptor = new TimerInterceptor();
        advisedSupport.setMethodInterceptor(timerInterceptor);

        // 3. 创建代理(Proxy)
        CglibProxy cglib2AopProxy = new CglibProxy(advisedSupport);
        HelloWorldService helloWorldServiceProxy = (HelloWorldService) cglib2AopProxy.getProxy();

        // 4. 基于AOP的调用
        helloWorldServiceProxy.helloWorld();
    }

}
