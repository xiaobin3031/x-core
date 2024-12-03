package com.xiaobin.core.json.model;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * created by xuweibin at 2024/8/12 10:53
 */
@Getter
@Setter
public class FieldMethodModel {

    private Field field;

    /**
     * 或者is
     */
    private Method getMethod;

    private Method setMethod;
}
