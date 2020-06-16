package com.github.beans.factory;

/**
 * bean的容器
 * @author plum-wine
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

}
