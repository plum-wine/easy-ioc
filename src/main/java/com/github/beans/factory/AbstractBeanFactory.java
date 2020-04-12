package com.github.beans.factory;

import com.github.annotation.AutoWired;
import com.github.beans.BeanPostProcessor;
import com.github.beans.definition.BeanDefinition;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author plum-wine
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    private final List<String> beanDefinitionNames = new ArrayList<>();

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        Object bean = beanDefinition.getBean();
        if (bean == null) {
            // do create bean
            bean = doCreateBean(beanDefinition);
            // init
            bean = initializeBean(bean, name);
            // 初始化后获得的bean 有可能是增强实现
            // 所以在beanDefinition中更新为增强的bean
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    /*
    此方法十分重要， 在初始化bean的同时，把所有的bean
    都传入进实现了BeanPostProcessor接口的实现类中，更新对象为process增强后的bean
    */
    protected Object initializeBean(Object bean, String name) throws Exception {
        // 初始化的时候 调用BeanPostProcessor

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            //初始化之前
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        // TODO:call initialize method
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            //初始化之后  对bean进行增强 原始的bean 被代理bean 的引用持有
            Object preBean = bean;
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
            //断言 增强后的 bean 是原始bean的父类
            assert preBean.getClass().isAssignableFrom(bean.getClass());
        }
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        Constructor<?>[] constructors = beanDefinition.getBeanClass().getConstructors();
        for (int i = 0; i < constructors.length; i++) {
            if (constructors[i].isAnnotationPresent(AutoWired.class) && (constructors[i].getAnnotation(AutoWired.class)).required()) {
                List<Object> objs = new ArrayList<>();
                for (Class<?> clazz : constructors[i].getParameterTypes()) {
                    List<Object> beansForType = getBeansForType(clazz);
                    if (beansForType.size() == 1) {
                        objs.add(beansForType.get(0));
                    } else {
                        // 存在两个及其以上的候选注入bean
                        // TODO 实现Qualifier
                    }
                }
                if (objs.size() == constructors[i].getParameterCount()) {
                    return constructors[i].newInstance(objs.toArray());
                } else {
                    throw new RuntimeException("构造注入缺少指定类型的bean");
                }
            }
        }
        return beanDefinition.getBeanClass().newInstance();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    public void preInstantiateSingletons() throws Exception {
        for (String beanName : this.beanDefinitionNames) {
            getBean(beanName);
        }
    }

    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = createBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    protected abstract void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception;

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    // 所有的bean都是从beanDefinition中取出来的,
    public List<Object> getBeansForType(Class<?> type) throws Exception {
        List<Object> beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }
}
