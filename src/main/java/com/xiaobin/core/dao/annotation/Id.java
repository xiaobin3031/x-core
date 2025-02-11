package com.xiaobin.core.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * created by xuweibin at 2024/11/25 16:02
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface Id {

    IdType type();

    enum IdType {
        AUTO, INPUT
    }
}
