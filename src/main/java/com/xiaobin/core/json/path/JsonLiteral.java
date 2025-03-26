package com.xiaobin.core.json.path;

/**
 * created by xuweibin at 2025/3/26
 */
class JsonLiteral extends JsonValue{

    private final Object value;

    JsonLiteral(Object value){
        this.value = value;
    }
}
