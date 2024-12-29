package com.xiaobin.core.lox;

import java.util.List;

/**
 * created by xuweibin at 2024/12/20 10:17
 */
interface LoxCallable {

    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
