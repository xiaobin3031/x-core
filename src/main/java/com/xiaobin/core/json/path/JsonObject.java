package com.xiaobin.core.json.path;

import com.xiaobin.core.bean.BeanManager;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * created by xuweibin at 2025/3/26
 */
class JsonObject extends JsonValue {

    final Map<String, JsonValue> valueMap = new HashMap<>();

    void add(String key, JsonValue value) {
        valueMap.put(key, value);
    }

    @Override
    public Object getValue(Class<?> cls) {
        BeanManager<?> beanManager = new BeanManager<>(cls);
        Object val = beanManager.newInstance();
        for (Map.Entry<String, JsonValue> entry : valueMap.entrySet()) {
            Field field = beanManager.getField(entry.getKey());
            if (field != null) {
                Class<?> type = field.getType();
                if (field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(Set.class)) {
                    if (field.getGenericType() instanceof ParameterizedType parameterizedType) {
                        type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    }
                }
                Object value = entry.getValue().getValue(type);
                if (value != null) {
                    beanManager.fillBean(val, entry.getKey(), value);
                }
            }
        }
        return val;
    }
}
