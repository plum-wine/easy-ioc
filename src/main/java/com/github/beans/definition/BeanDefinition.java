package com.github.beans.definition;

import com.github.beans.PropertyValues;
import lombok.Data;

/**
 * bean的内容及元数据，保存在BeanFactory中，包装bean的实体
 * @author plum-wine
 */
@Data
public class BeanDefinition {

	private Object bean;

	private Class<?> beanClass;

	private String beanClassName;

	private PropertyValues propertyValues = new PropertyValues();

	public BeanDefinition() {
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
