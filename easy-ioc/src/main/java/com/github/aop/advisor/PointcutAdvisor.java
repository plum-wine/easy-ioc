package com.github.aop.advisor;


import com.github.aop.pointcut.Pointcut;

public interface PointcutAdvisor extends Advisor {

   Pointcut getPointcut();

}
