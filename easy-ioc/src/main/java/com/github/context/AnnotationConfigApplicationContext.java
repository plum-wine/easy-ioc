package com.github.context;

import com.github.beans.definition.BeanDefinition;
import com.github.beans.definition.impl.AnnotationBeanDefinitionReader;
import com.github.beans.factory.AbstractBeanFactory;
import com.github.beans.factory.AutowireCapableBeanFactory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hangs.zhang
 * @date 2020/6/24 上午10:12
 * *********************
 * function:
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private final String packages;

    public AnnotationConfigApplicationContext(String packages) {
        this(packages, new AutowireCapableBeanFactory());
    }

    private AnnotationConfigApplicationContext(String packages, AbstractBeanFactory beanFactory) {
        super(beanFactory);
        this.packages = packages;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) {
        AnnotationBeanDefinitionReader annotationBeanDefinitionReader = new AnnotationBeanDefinitionReader();
        annotationBeanDefinitionReader.loadBeanDefinitions(this.packages);

        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : annotationBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }

    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        List<Object> beans = beanFactory.getBeansForType(Object.class);
        return beans.stream()
                .filter(clazz -> clazz.getClass().isAnnotationPresent(annotation))
                .map(Object::getClass)
                .collect(Collectors.toSet());
    }

}
