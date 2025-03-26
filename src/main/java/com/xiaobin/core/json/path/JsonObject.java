package com.xiaobin.core.json.path;

import java.util.HashMap;
import java.util.Map;

/**
 * created by xuweibin at 2025/3/26
 */
class JsonObject extends JsonValue {

    private final Map<String, JsonValue> valueMap = new HashMap<>();

    void add(String key, JsonValue value) {
        valueMap.put(key, value);
    }
}
