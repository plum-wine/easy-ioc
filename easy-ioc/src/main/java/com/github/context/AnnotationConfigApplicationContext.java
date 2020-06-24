package com.github.context;

import com.github.beans.definition.BeanDefinition;
import com.github.beans.definition.impl.AnnotationBeanDefinitionReader;
import com.github.beans.factory.AbstractBeanFactory;
import com.github.beans.factory.AutowireCapableBeanFactory;

import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/6/24 上午10:12
 * *********************
 * function:
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private final String packages;

    public AnnotationConfigApplicationContext(String packages) throws Exception {
        this(packages, new AutowireCapableBeanFactory());
    }

    public AnnotationConfigApplicationContext(String packages, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.packages = packages;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
        AnnotationBeanDefinitionReader annotationBeanDefinitionReader = new AnnotationBeanDefinitionReader();
        annotationBeanDefinitionReader.loadBeanDefinitions(this.packages);

        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : annotationBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }
}
