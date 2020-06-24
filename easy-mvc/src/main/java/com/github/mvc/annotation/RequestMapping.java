package com.github.mvc.annotation;

import com.github.mvc.type.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:38
 * *****************
 * function:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequestMapping {

    String value();

    RequestMethod method() default RequestMethod.GET;

}
