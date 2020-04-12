package com.github.message;

import java.lang.annotation.*;

/**
 * @author winters
 * 创建时间：13/03/2018 15:52
 * 创建原因：
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EventListener {
}
