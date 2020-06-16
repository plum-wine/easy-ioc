package com.github.annotation;

import java.lang.annotation.*;


@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AutoWired {

    boolean required() default true;

}
