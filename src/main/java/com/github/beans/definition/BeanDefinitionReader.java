package com.github.beans.definition;

/**
 * 从配置中读取BeanDefinition
 * @author plum-wine
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;

}
