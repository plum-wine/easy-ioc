package com.github.annotation.component;

import java.lang.annotation.*;

/**
 * @author hangs.zhang
 * @date 2020/06/20 12:00
 * *****************
 * function:
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
}
