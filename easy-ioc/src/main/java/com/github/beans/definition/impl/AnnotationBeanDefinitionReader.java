package com.github.beans.definition.impl;

import com.github.annotation.component.Component;
import com.github.annotation.component.Controller;
import com.github.annotation.component.Repository;
import com.github.annotation.component.Service;
import com.github.beans.definition.AbstractBeanDefinitionReader;
import com.github.beans.definition.BeanDefinition;
import com.github.mvc.utils.ClassUtils;
import com.google.common.collect.Sets;

import java.lang.annotation.Annotation;
import java.util.Set;

import static com.github.mvc.utils.MyStringUtils.toLowerCaseFirstOne;

/**
 * @author hangs.zhang
 * @date 2020/6/24 下午7:33
 * *********************
 * function:
 */
public class AnnotationBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private static final Set<Class<? extends Annotation>> COMPONENTS
            = Sets.newHashSet(Component.class, Repository.class, Service.class, Controller.class);

    @Override
    public void loadBeanDefinitions(String packages) {
        Set<Class<?>> classes = ClassUtils.extractPackageClass(packages);
        classes.forEach(clazz -> {
            for (Class<? extends Annotation> annotation : COMPONENTS) {
                if (clazz.isAnnotationPresent(annotation)) {
                    processBeanDefinition(clazz);
                }
            }
        });
    }

    private void processBeanDefinition(Class<?> clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setBeanClassName(clazz.getName());
        getRegistry().put(toLowerCaseFirstOne(clazz.getSimpleName()), beanDefinition);
    }

}
