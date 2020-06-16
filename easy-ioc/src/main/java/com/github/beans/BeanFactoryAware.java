package com.github.beans;

import com.github.beans.factory.BeanFactory;

/**
 * @author plum-wine
 * 此接口主要的目的，是将BeanFactory的引用 注入进一些特殊的bean中，例如 BeanProcessor的实现
 * AspectJAwareAdvisorAutoProxyCreator 需要处理一些AOP相关的代理
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws Exception;

}
