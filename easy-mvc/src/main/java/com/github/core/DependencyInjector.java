package com.github.core;

import com.github.annotation.inject.Autowired;
import com.github.utils.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

/**
 * @author hangs.zhang
 * @date 2020/06/20 22:31
 * *****************
 * function:
 */
public class DependencyInjector {

    private BeanContainer beanContainer;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public DependencyInjector() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doIoc() {
        Set<Class<?>> classes = beanContainer.getClasses();
        classes.forEach(clazz -> {
            Field[] fields = clazz.getDeclaredFields();
            if (Objects.isNull(fields)) {
                return;
            }
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    String autowiredValue = field.getAnnotation(Autowired.class).value();
                    Class<?> fieldClass = field.getType();
                    Object fieldValue = getFieldInstance(fieldClass, autowiredValue);
                    if (Objects.isNull(fieldValue)) {
                        throw new RuntimeException("can`t find fieldValue, field is" + fieldClass.getName());
                    } else {
                        Object bean = beanContainer.getBean(clazz);
                        ClassUtils.setFieldValue(field, bean, fieldValue);
                    }
                }
            }
        });
    }

    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        Object bean = beanContainer.getBean(fieldClass);
        if (!Objects.isNull(bean)) {
            return bean;
        } else {
            Class<?> implClass = getImplClass(fieldClass, autowiredValue);
            if (!Objects.isNull(implClass)) {
                return beanContainer.getBean(implClass);
            } else {
                return null;
            }
        }
    }

    private Class<?> getImplClass(Class<?> fieldClass, String autowiredValue) {
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(fieldClass);
        if (StringUtils.isEmpty(autowiredValue)) {
            if (classSet.size() == 1) {
                return classSet.iterator().next();
            } else {
                throw new RuntimeException("multi impl, need set autowired value");
            }
        } else {
            for (Class<?> clazz : classSet) {
                if (Objects.equals(clazz.getSimpleName(), autowiredValue)) {
                    return clazz;
                }
            }
        }
        return null;
    }

}
