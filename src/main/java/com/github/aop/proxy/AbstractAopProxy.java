package com.github.aop.proxy;

import com.github.aop.AdvisedSupport;
import com.github.aop.proxy.AopProxy;

/**
 * @author plum-wine
 */
public abstract class AbstractAopProxy implements AopProxy {

    protected AdvisedSupport advised;

    public AbstractAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

}
