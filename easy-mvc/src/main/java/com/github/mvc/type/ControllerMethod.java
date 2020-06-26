package com.github.mvc.type;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:45
 * *****************
 * function:
 */
@Data
public class ControllerMethod {
    private String beanName;
    // controller对应的class对象
    private Class<?> controllerClass;
    // 执行的方法实例
    private Method invokeMethod;
    // 方法名称以及参数
    private Map<String, Class<?>> methodParameters;

    public ControllerMethod(String beanName, Class<?> controllerClass, Method invokeMethod, Map<String, Class<?>> methodParameters) {
        this.beanName = beanName;
        this.controllerClass = controllerClass;
        this.invokeMethod = invokeMethod;
        this.methodParameters = methodParameters;
    }
}
