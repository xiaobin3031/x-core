package com.xiaobin.core.util;

/**
 * created by xuweibin at 2023/2/7
 */
public class NumberUtil {

    public static boolean intEqual(Integer i, int s) {
        return i != null && s == i;
    }

    public static boolean shortEquals(Short value, int s) {
        return value != null && value == s;
    }

    public static boolean positiveInt(Integer id) {
        return id != null && id > 0;
    }

    public static boolean positiveLong(Long id) {
        return id != null && id > 0;
    }
}
