package com.github.context;

import com.github.beans.factory.AbstractBeanFactory;

/**
 * @author hangs.zhang
 * @date 2020/6/24 上午10:12
 * *********************
 * function:
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    public AnnotationConfigApplicationContext(AbstractBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {

    }
}
