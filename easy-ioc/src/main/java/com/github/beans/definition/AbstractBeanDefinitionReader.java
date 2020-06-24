package com.github.beans.definition;

import com.github.beans.io.ResourceLoader;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 从配置中读取BeanDefinition
 *
 * @author plum-wine
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final Map<String, BeanDefinition> registry = Maps.newHashMap();

    protected ResourceLoader resourceLoader;

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
