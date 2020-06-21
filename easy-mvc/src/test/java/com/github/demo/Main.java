package com.github.demo;

import com.github.core.BeanContainer;
import com.github.core.DependencyInjector;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/06/20 23:06
 * *****************
 * function:
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void hello() {
        BeanContainer instance = BeanContainer.getInstance();
        instance.loadBeans();

        DependencyInjector dependencyInjector = new DependencyInjector();
        dependencyInjector.doIoc();

        FoobarController foobarController = (FoobarController) instance.getBean(FoobarController.class);
        LOGGER.info("result:{}", foobarController.hello("message"));
    }

}
