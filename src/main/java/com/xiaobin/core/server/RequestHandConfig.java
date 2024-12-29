package com.xiaobin.core.server;

import com.xiaobin.core.server.exception.HttpServerException;
import com.xiaobin.core.server.model.RequestInfo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuweibin
 * @date 12/29/24
 * @description
 */
class RequestHandConfig {

    private final Map<String, RequestInfo> requestInfoMap = new HashMap<>();

    void defHandler(String path, Method method, String requestMethod, Object instance) {
        if(requestMethod.contains(path)) throw new HttpServerException("duplicate path: " + path);
        path = path.replaceAll("//", "/");
        if(!path.startsWith("/")) path = "/" + path;
        requestInfoMap.put(path, new RequestInfo(method, requestMethod, instance));
    }

    RequestInfo getHandler(String path, String requestMethod) {
        RequestInfo info = requestInfoMap.get(path);
        if(info == null) throw new HttpServerException("path not register: " + path);

        if(info.requestMethod() != null && !info.requestMethod().equalsIgnoreCase(requestMethod))
            throw new HttpServerException("request method [" + requestMethod + "] not supported, path: " + path);

        return info;
    }
}
