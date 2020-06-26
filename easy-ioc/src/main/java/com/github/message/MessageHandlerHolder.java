package com.github.message;


import com.github.beans.BeanPostProcessor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MessageHandlerHolder implements BeanPostProcessor {

    private final List<MessageHandlerInvocation> messageHandlers = new ArrayList<>();

    public List<MessageHandlerInvocation> getMessageHandlers() {
        return messageHandlers;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventListener.class)) {
                if (method.getParameters().length == 1) {
                    MessageHandlerInvocation messageHandlerInvocation = new MessageHandlerInvocation(
                            method,
                            method.getParameters()[0], bean);
                    messageHandlers.add(messageHandlerInvocation);
                }
            }
        }
        return bean;
    }

}
