package com.xiaobin.core.json.path;

import com.xiaobin.core.bean.BeanManager;

/**
 * created by xuweibin at 2025/3/26
 */
class JsonString extends JsonValue {

    private final String value;

    JsonString(String value) {
        this.value = value;
    }

    @Override
    public Object getValue(Class<?> cls) {
        return BeanManager.convertValue(value, cls);
    }
}
