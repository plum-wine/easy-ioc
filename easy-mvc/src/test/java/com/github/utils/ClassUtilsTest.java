package com.github.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Set;

/**
 * @author hangs.zhang
 * @date 2020/06/20 13:06
 * *****************
 * function:
 */
public class ClassUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void extractPackageClass() {
        Set<Class<?>> classes = ClassUtils.extractPackageClass("com.github");
        classes.forEach(clazz -> LOGGER.info(clazz.getName()));


    }
}