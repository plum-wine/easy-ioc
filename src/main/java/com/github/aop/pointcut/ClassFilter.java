package com.github.aop.pointcut;

public interface ClassFilter {

    boolean matches(Class targetClass);
}
