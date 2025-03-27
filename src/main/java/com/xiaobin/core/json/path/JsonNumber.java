package com.xiaobin.core.json.path;

import com.xiaobin.core.bean.BeanManager;

/**
 * created by xuweibin at 2025/3/26
 */
class JsonNumber extends JsonValue {

    private final String number;

    JsonNumber(String number) {
        this.number = number;
    }

    @Override
    public Object getValue(Class<?> cls) {
        return BeanManager.convertValue(number, cls);
    }
}
