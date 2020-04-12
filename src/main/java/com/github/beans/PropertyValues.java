package com.github.beans;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 包装一个对象所有的PropertyValue
 * 为什么封装而不是直接用List?因为可以封装一些操作。
 */
@Getter
@ToString
public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public PropertyValues() {
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        //TODO:这里可以对于重复propertyName进行判断，直接用list没法做到
        this.propertyValues.add(propertyValue);
    }

}
