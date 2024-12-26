package com.xiaobin.core.lox;

/**
 * created by xuweibin at 2024/12/20 13:05
 */
class Return extends RuntimeException {

    final Object value;

    Return(Object value) {
        super(null, null, false, false);
        this.value = value;
    }
}
