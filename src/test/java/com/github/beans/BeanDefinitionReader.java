package com.github.beans;

import com.github.beans.definition.BeanDefinition;
import com.github.beans.io.ResourceLoader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;

/**
 * @author plum-wine
 * @date 2020/04/12 17:25
 * *****************
 * function:
 */
public class BeanDefinitionReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void test() throws Exception {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        beanDefinitionReader.loadBeanDefinitions("applicationContext.xml");

        Map<String, BeanDefinition> registry = beanDefinitionReader.getRegistry();
        registry.forEach((key, value) -> {
            LOGGER.info("key:{}", key);
            LOGGER.info("value:{}", value);
        });

    }

}
