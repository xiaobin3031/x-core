package com.xiaobin.core.dao.annotation;

/**
 * created by xuweibin at 2024/11/25 16:02
 */
public @interface Id {

    IdType type();

    enum IdType {
        AUTO, INPUT
    }
}
