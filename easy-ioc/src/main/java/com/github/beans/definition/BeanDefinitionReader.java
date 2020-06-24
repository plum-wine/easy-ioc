package com.github.beans.definition;

/**
 * 从配置中读取BeanDefinition
 * @author plum-wine
 */
public interface BeanDefinitionReader {

    /**
     * 解析加载BeanDefinition
     * @param location 可以为classpath或者packages
     * @throws Exception
     */
    void loadBeanDefinitions(String location) throws Exception;

}
