package com.github.aop;


import com.github.aop.proxy.AopProxy;
import com.github.aop.proxy.CglibProxy;

public class ProxyFactory extends AdvisedSupport implements AopProxy {

	@Override
	public Object getProxy() {
		return createAopProxy().getProxy();
	}

	protected final AopProxy createAopProxy() {
		return new CglibProxy(this);
	}

}
