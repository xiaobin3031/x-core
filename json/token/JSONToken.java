package com.xiaobin.core.json.token;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * created by xuweibin at 2024/12/3 13:17
 */
@Getter
public abstract class JSONToken<T> {

    protected final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected final T value;

    public JSONToken(T value) {
        this.value = value;
    }

    public abstract Object evaluate(Class<?> cls);
}
