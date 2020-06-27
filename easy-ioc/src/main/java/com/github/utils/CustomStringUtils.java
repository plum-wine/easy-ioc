package com.github.utils;

/**
 * @author hangs.zhang
 * @date 2020/06/26 12:23
 * *****************
 * function:
 */
public final class CustomStringUtils {

    private CustomStringUtils() {
    }

    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        } else {
            return Character.toLowerCase(str.charAt(0)) + str.substring(1);
        }
    }

}
