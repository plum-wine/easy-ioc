package com.github.beans.factory;

import com.github.annotation.AutoWired;
import com.github.beans.BeanPostProcessor;
import com.github.beans.definition.BeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

    /**
     * 此方法十分重要， 在初始化bean的同时，把所有的bean
     * 都传入进实现了BeanPostProcessor接口的实现类中，更新对象为process增强后的bean
     */
    protected Object initializeBean(Object bean, String name) throws Exception {
        // 初始化的时候 调用BeanPostProcessor
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
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

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    /**
     * 载入所有的bean
     */
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

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        Constructor<?>[] constructors = beanDefinition.getBeanClass().getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(AutoWired.class) && (constructor.getAnnotation(AutoWired.class)).required()) {
                List<Object> objs = new ArrayList<>();
                for (Class<?> clazz : constructor.getParameterTypes()) {
                    List beansForType = getBeansForType(clazz);
                    if (beansForType.size() == 1) {
                        objs.add(beansForType.get(0));
                    } else {
                        // TODO 实现Qualifier
                        LOGGER.info("Qualifier暂未实现");
                    }
                }
                if (objs.size() == constructor.getParameterCount()) {
                    return constructor.newInstance(objs.toArray());
                } else {
                    throw new RuntimeException("构造注入缺少指定类型的bean");
                }
            }
        }
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 模板方法,交由子类去实现属性注入,父类控制注入流程
     */
    protected abstract void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception;

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 根据class获取bean
     */
    public List getBeansForType(Class<?> type) throws Exception {
        List beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }
}
