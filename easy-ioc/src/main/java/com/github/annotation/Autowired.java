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

    boolean required() default true;

}
