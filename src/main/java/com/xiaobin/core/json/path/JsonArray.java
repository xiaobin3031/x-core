package com.xiaobin.core.json.path;

import java.util.ArrayList;
import java.util.List;

/**
 * created by xuweibin at 2025/3/26
 */
class JsonArray extends JsonValue {

    final List<JsonValue> valueList = new ArrayList<>();

    private int size;

    int getSize() {
        return size;
    }

    void add(JsonValue value) {
        valueList.add(value);
        size++;
    }

    @Override
    public Object getValue(Class<?> cls) {
        List<Object> list = new ArrayList<>();
        if (size > 0) {
            for (JsonValue jsonValue : valueList) {
                Object value = jsonValue.getValue(cls);
                if (value != null) {
                    list.add(value);
                }
            }
        }
        return list;
    }
}
