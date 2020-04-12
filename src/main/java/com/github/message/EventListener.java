package com.github.message;

import java.lang.annotation.*;

/**
 * @author plum-wine
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EventListener {
}
