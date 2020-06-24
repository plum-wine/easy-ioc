package com.github.beans.factory;

import com.github.annotation.Autowired;
import com.github.beans.BeanFactoryAware;
import com.github.beans.BeanReference;
import com.github.beans.PropertyValue;
import com.github.beans.definition.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 可自动装配内容的BeanFactory
 * @author plum-wine
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        applyPropertyValueForSetter(bean, beanDefinition);
        applyPropertyValuesForField(bean);
    }

    private void applyPropertyValueForSetter(Object bean, BeanDefinition beanDefinition) throws Exception {
        for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }

            try {
                // 拼出setter方法
                Method declaredMethod = bean.getClass().getDeclaredMethod("set" + propertyValue.getName().substring(0, 1).toUpperCase() + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }
    }

    private void applyPropertyValuesForField(Object bean) throws Exception {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class) && field.getAnnotation(Autowired.class).required()) {
                field.setAccessible(true);
                List<Object> beansForType = getBeansForType(field.getType());
                //todo implement qualifier
                if (beansForType.size() != 1) {
                    throw new RuntimeException("可选注入类型 超过1个, 或者没有可选注入类型");
                }
                field.set(bean, beansForType.get(0));
            }
        }
    }

}
