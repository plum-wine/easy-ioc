package com.github.context;

import com.github.beans.factory.AbstractBeanFactory;
import com.github.beans.factory.AutowireCapableBeanFactory;

/**
 * @author hangs.zhang
 * @date 2020/6/24 上午10:12
 * *********************
 * function:
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private String packages;

    public AnnotationConfigApplicationContext(String packages) throws Exception {
        this(packages, new AutowireCapableBeanFactory());
        this.packages = packages;
    }

    public AnnotationConfigApplicationContext(String packages, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {

    }
}
