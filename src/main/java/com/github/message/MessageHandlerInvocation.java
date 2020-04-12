package com.github.message;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Getter
public class MessageHandlerInvocation {

    private Method method;

    private Object target;

    private Parameter parameterType;

    public MessageHandlerInvocation(Method method, Parameter parameter, Object target) {
        this.method = method;
        this.parameterType = parameter;
        this.target = target;
    }

    public void handleMessage(Object object) throws InvocationTargetException, IllegalAccessException {
        method.invoke(target, object);
    }
}
