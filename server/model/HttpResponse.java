package com.xiaobin.core.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * created by xuweibin at 2024/11/26 9:47
 */
@Getter
@Setter
@Builder
public class HttpResponse<T> {

    private int code;

    private String msg;

    private T data;
}
