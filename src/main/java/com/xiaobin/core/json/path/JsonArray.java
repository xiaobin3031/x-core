package com.xiaobin.core.json.path;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * created by xuweibin at 2025/3/26
 */
class JsonArray extends JsonValue {

    private final List<JsonValue> valueList = new ArrayList<>();

    @Getter
    int size;

    void add(JsonValue value) {
        valueList.add(value);
    }
}
