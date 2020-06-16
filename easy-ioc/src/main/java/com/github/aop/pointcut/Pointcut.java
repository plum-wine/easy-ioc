package com.github.aop.pointcut;

/**
 * Jointpoint 织入操作的系统执行点
 * Pointcut则是Jointpoint的表述方式
 */
public interface Pointcut {

    /**
     * 匹配织入操作的对象
     */
    ClassFilter getClassFilter();

    /**
     * 匹配织入的方法
     */
    MethodMatcher getMethodMatcher();

}
