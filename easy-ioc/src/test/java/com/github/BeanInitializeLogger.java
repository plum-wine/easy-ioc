package com.github;

import com.github.beans.BeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;


public class BeanInitializeLogger implements BeanPostProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		LOGGER.info("Initialize bean " + beanName + " start!");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		LOGGER.info("Initialize bean " + beanName + " end!");
		return bean;
	}
}
