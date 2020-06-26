package com.github.context;

import com.github.beans.BeanPostProcessor;
import com.github.beans.definition.BeanDefinition;
import com.github.beans.factory.AbstractBeanFactory;
import com.github.message.MessageHandlerHolder;
import com.github.message.MessageHandlerInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author plum-wine
 * applicationContext的模板类,对BeanFacotry进行增强
 * 事件,注册BeanPostProcessor,自动加载BeanDefinition,加载所有bean
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected AbstractBeanFactory beanFactory;

    private final MessageHandlerHolder messageHandlerHolder = new MessageHandlerHolder();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void refresh() {
        loadBeanDefinitions(beanFactory);
        //抽象应用上下文 向beanFactory注册beanPostProcessor
        registerBeanPostProcessors(beanFactory);
        //消息处理者handler 扫描所有的bean 对方法上有EventListener注解的
        beanFactory.addBeanPostProcessor(messageHandlerHolder);
        //将自身注入进bean容器
        registerSelf();
        onRefresh();
    }

    /**
     * 由子类去实现如何获取BeanDefinition
     * 可以是xml或者annotation等方式
     */
    protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory);

    private void registerSelf() {
        BeanDefinition self = new BeanDefinition();
        self.setBeanClassName(this.getClass().getName());
        self.setBeanClass(this.getClass());
        self.setBean(this);
        beanFactory.registerBeanDefinition("applicationContext", self);
    }

    protected void onRefresh() {
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 从beanFactory中取出所有的BeanPostProcessor接口的实现类
     */
    protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) {
        List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
        // 全部加入到beanFactory的 beanPostProcessors 引用中去
        for (Object beanPostProcessor : beanPostProcessors) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessor);
        }
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    @Override
    public void publishEvent(Object object) {
        for (MessageHandlerInvocation messageHandlerInvocation : messageHandlerHolder.getMessageHandlers()) {
            if (messageHandlerInvocation.getParameterType().getType().isInstance(object)) {
                try {
                    messageHandlerInvocation.handleMessage(object);
                } catch (Exception e) {
                    LOGGER.error("handleMessage error", e);
                }
            }
        }
    }

}
