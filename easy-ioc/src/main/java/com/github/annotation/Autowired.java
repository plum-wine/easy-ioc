package com.github.annotation;

import java.lang.annotation.*;


/**
 * @author plum-wine
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface Autowired {

    String value() default "";

    boolean required() default true;

}
