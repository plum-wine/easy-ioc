package com.github.beans;

import lombok.Data;

/**
 * 用于bean的属性注入
 *
 * @author plum-wine
 */
@Data
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
