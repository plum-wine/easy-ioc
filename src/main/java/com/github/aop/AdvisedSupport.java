package com.github.aop;

import com.github.aop.pointcut.MethodMatcher;
import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * 代理相关的元数据
 */
@Data
public class AdvisedSupport {

	private TargetSource targetSource;

	private MethodInterceptor methodInterceptor;

	private MethodMatcher methodMatcher;

}
