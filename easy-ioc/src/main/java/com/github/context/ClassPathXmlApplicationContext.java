package com.github.context;


import com.github.beans.definition.BeanDefinition;
import com.github.beans.definition.impl.XmlBeanDefinitionReader;
import com.github.beans.factory.AbstractBeanFactory;
import com.github.beans.factory.AutowireCapableBeanFactory;
import com.github.beans.io.ResourceLoader;

import java.util.Map;

/**
 * @author plum-wine
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private final String configLocation;

    public ClassPathXmlApplicationContext(String configLocation) {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }

}
