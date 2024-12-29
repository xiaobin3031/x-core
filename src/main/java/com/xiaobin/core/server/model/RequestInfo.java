package com.xiaobin.core.server.model;

import java.lang.reflect.Method;

/**
 * @author xuweibin
 * @date 12/29/24
 * @description
 */
public record RequestInfo(Method method, String requestMethod, Object instance) {
}
