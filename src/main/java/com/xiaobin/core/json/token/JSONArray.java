package com.xiaobin.core.json.token;

import com.xiaobin.core.json.exception.JSONParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * created by xuweibin at 2024/12/10 17:27
 */
public class JSONArray extends JSONToken<List<JSONToken<?>>> {

    public JSONArray(List<JSONToken<?>> value) {
        super(value);
    }

    @Override
    public Object evaluate(Class<?> cls) {
        if (super.value != null && !super.value.isEmpty()) {
            if (cls.isAssignableFrom(List.class)) {
                List<Object> list = new ArrayList<>();
                for (JSONToken<?> jsonToken : super.value) {

                }
                return list;
            } else if (cls.isAssignableFrom(Set.class)) {

            }
            throw new JSONParseException("json array can't convert to " + cls);
        }

        return null;
    }
}
