package com.github.aop.advisor;

import org.aopalliance.aop.Advice;

/**
 * Advice 单一横切关注点的逻辑载体,代表织入到Joinpoint的横切逻辑
 */
public interface Advisor {

    Advice getAdvice();

}
