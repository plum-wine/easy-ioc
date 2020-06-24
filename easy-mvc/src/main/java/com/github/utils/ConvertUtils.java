package com.github.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author hangs.zhang
 * @date 2020/06/24 09:14
 * *****************
 * function:
 */
public class ConvertUtils {

    public static Object convert(Class<?> type, String requestValue) {
        if (isPrimitive(type)) {
            if (StringUtils.isNotBlank(requestValue)) {
                if (type.equals(int.class) || type.equals(Integer.class)) {
                    return Integer.parseInt(requestValue);
                } else if (type.equals(String.class)) {
                    return requestValue;
                }
            }
            return requestValue;
        } else {
            throw new RuntimeException("not support");
        }
    }

    private static boolean isPrimitive(Class<?> type) {
        return type.equals(int.class) || type.equals(Integer.class) || type.equals(String.class);
    }

    public static Object primitiveNull(Class<?> type) {
        if (type == int.class || type == long.class) {
            return 0;
        } else if (type == boolean.class) {
            return false;
        }
        return null;
    }

}
