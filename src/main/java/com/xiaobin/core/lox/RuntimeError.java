package com.xiaobin.core.lox;

/**
 * created by xuweibin at 2024/12/19 16:49
 */
class RuntimeError extends RuntimeException {

    final Token token;

    RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }
}
