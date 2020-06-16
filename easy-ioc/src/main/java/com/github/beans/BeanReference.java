package com.github.beans;

import lombok.Data;

/**
 * @author plum-wine
 * @date 2020/04/12 17:07
 * *****************
 * function: bean对象的holder
 */
@Data
public class BeanReference {

    private String name;

    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

}
