package com.github.context;

import com.github.beans.BeanPostProcessor;
import com.github.beans.definition.BeanDefinition;
import com.github.beans.factory.AbstractBeanFactory;

import java.util.List;

/**
 * @author plum-wine
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void refresh() throws Exception {
        loadBeanDefinitions(beanFactory);
        //抽象应用上下文 向beanFactory注册beanPostProcessor
        registerBeanPostProcessors(beanFactory);
        //将自身注入进bean容器
        registerSelf();
        onRefresh();
    }

    private void registerSelf() throws Exception {
        BeanDefinition self = new BeanDefinition();
        self.setBeanClassName(this.getClass().getName());
        self.setBeanClass(this.getClass());
        self.setBean(this);
        beanFactory.registerBeanDefinition("applicationContext", self);
    }

    protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception;

    //从beanFactory中 拿出所有的 BeanPostProcessor接口的实现类
    protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
        List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
        //全部加入到beanFactory的 beanPostProcessors 引用中去
        for (Object beanPostProcessor : beanPostProcessors) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessor);
        }
    }

    protected void onRefresh() throws Exception {
        beanFactory.preInstantiateSingletons();
    }

    @Override
    public Object getBean(String name) throws Exception {
        return beanFactory.getBean(name);
    }

}
