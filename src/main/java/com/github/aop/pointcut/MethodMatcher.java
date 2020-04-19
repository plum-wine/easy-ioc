package com.github.aop.pointcut;

import java.lang.reflect.Method;

/**
 * @author plum-wine
 */
public interface MethodMatcher {

    boolean matches(Method method, Class targetClass);

}
