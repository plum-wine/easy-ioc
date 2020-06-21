package com.github.core;

import com.github.annotation.Component;
import com.github.annotation.Controller;
import com.github.annotation.Repository;
import com.github.annotation.Service;
import com.github.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author hangs.zhang
 * @date 2020/06/20 21:06
 * *****************
 * function:
 */
public class BeanContainer {

    private final ConcurrentHashMap<Class<?>, Object> classBeans = new ConcurrentHashMap<>();

    private boolean isLoad = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final List<Class<? extends Annotation>> BEAN_ANNOTATIONS = Arrays.asList(Component.class, Repository.class, Service.class, Controller.class);

    private BeanContainer() {
    }

    public enum BeanContainerHolder {

        HOLDER;

        public BeanContainer instance;

        BeanContainerHolder() {
            this.instance = new BeanContainer();
        }

    }

    public static BeanContainer getInstance() {
        return BeanContainerHolder.HOLDER.instance;
    }

    public synchronized void loadBeans() {
        if (isLoad) {
            return;
        }
        Set<Class<?>> classes = ClassUtils.extractPackageClass("com.github");
        classes.forEach(clazz -> {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATIONS) {
                if (clazz.isAnnotationPresent(annotation)) {
                    classBeans.put(clazz, ClassUtils.newInstance(clazz, true));
                }
            }
        });

        isLoad = true;
    }

    public Set<Class<?>> getClasses() {
        return classBeans.keySet();
    }

    public Set<Class<?>> getClassesByAnnotation(Annotation annotation) {
        return getClasses().stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation.getClass()))
                .collect(Collectors.toSet());
    }

    public Set<Class<?>> getClassesBySuper(Class<?> interfaceClazz) {
        return getClasses().stream()
                .filter(clazz -> !Objects.equals(clazz, interfaceClazz))
                .filter(interfaceClazz::isAssignableFrom)
                .collect(Collectors.toSet());
    }

    public Set<Object> getBeans() {
        return new HashSet<>(classBeans.values());
    }

    public Object getBean(Class<?> clazz) {
        return classBeans.get(clazz);
    }

    public Object addBean(Class<?> clazz, Object bean) {
        return classBeans.put(clazz, bean);
    }

    public Object removeBean(Class<?> clazz) {
        return classBeans.remove(clazz);
    }

}
