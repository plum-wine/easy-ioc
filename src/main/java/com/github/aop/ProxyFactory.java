package com.github.aop;

import com.github.aop.advisor.AdvisedSupport;
import com.github.aop.proxy.AopProxy;
import com.github.aop.proxy.CglibProxy;

/**
 * 织入器,Weaver,将横切逻辑织入到OOP中
 */
public class ProxyFactory extends AdvisedSupport implements AopProxy {

	@Override
	public Object getProxy() {
		return createAopProxy().getProxy();
	}

	protected final AopProxy createAopProxy() {
		return new CglibProxy(this);
	}

}
