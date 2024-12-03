package com.xiaobin.core.server;

/**
 * created by xuweibin at 2024/11/26 9:47
 */
public interface MyHttpHandler<T> {

    T handle(String body);
}
