package com.github.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class TimerInterceptor implements MethodInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	//代理回调
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long time = System.nanoTime();
		LOGGER.info("Invocation of Method " + invocation.getMethod().getName() + " start!");
		//执行真正的bean方法前

		//执行bean原始方法
		Object proceed = invocation.proceed();
		//执行真正的bean方法后
		LOGGER.info("Invocation of Method {} end! takes {} nanoseconds", invocation.getMethod().getName(), (System.nanoTime() - time));
		return proceed;
	}

}
