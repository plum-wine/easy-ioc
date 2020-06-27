package com.github.beans.factory;

import com.github.annotation.Autowired;
import com.github.beans.BeanFactoryAware;
import com.github.beans.BeanReference;
import com.github.beans.PropertyValue;
import com.github.beans.definition.BeanDefinition;
import com.github.utils.BeanUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author plum-wine
 * @function 可自动装配内容的BeanFactory
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        applyPropertyValueForSetter(bean, beanDefinition);
        applyPropertyValuesForField(bean);
    }

    private void applyPropertyValueForSetter(Object bean, BeanDefinition beanDefinition) {
        for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }

            try {
                // 拼出setter方法
                String setter = "set" + propertyValue.getName().substring(0, 1).toUpperCase() + propertyValue.getName().substring(1);
                BeanUtils.invokeMethod(setter, bean, value);
            } catch (Exception ex) {
                BeanUtils.setFieldValue(propertyValue.getName(), bean, value);
            }
        }
    }

    private void applyPropertyValuesForField(Object bean) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class) && field.getAnnotation(Autowired.class).required()) {
                List<Object> beansForType = getBeansForType(field.getType());
                //todo implement qualifier
                if (beansForType.size() != 1) {
                    throw new RuntimeException("可选注入类型 超过1个, 或者没有可选注入类型");
                }
                BeanUtils.setFieldValue(field, bean, beansForType.get(0));
            }
        }
    }

}
