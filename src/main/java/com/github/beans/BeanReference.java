package com.github.beans;

import lombok.Data;

/**
 * @author plum-wine
 */
@Data
public class BeanReference {

    private String name;

    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

}
