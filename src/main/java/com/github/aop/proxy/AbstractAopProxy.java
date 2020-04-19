package com.github.aop.proxy;

import com.github.aop.advisor.AdvisedSupport;

/**
 * @author plum-wine
 */
public abstract class AbstractAopProxy implements AopProxy {

    protected AdvisedSupport advised;

    public AbstractAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

}
