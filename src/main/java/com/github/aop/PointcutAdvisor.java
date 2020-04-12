package com.github.aop;


public interface PointcutAdvisor extends Advisor {

   Pointcut getPointcut();

}
