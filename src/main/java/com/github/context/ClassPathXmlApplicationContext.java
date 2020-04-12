package com.github.context;


import com.github.beans.definition.BeanDefinition;
import com.github.beans.factory.AbstractBeanFactory;
import com.github.beans.factory.AutowireCapableBeanFactory;
import com.github.beans.io.ResourceLoader;
import com.github.beans.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @author plum-wine
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private String configLocation;


    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }

}
