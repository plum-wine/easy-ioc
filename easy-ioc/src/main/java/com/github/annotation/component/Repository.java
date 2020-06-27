package com.github.annotation.component;

import java.lang.annotation.*;

/**
 * @author hangs.zhang
 * @date 2020/06/20 12:01
 * *****************
 * function:
 */
@Component
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {

    String value() default "";

}
