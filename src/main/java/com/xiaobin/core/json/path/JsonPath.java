package com.xiaobin.core.json.path;

import com.xiaobin.core.json.exception.JSONParseException;
import com.xiaobin.core.log.SysLogUtil;

import java.util.List;

/**
 * created by xuweibin at 2025/3/26
 */
public class JsonPath {

    private final JsonValue topJsonValue;
    private final String source;

    public JsonPath(String source) {
        this.source = source;
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanToken();
        Resolver resolver = new Resolver(tokens);
        this.topJsonValue = resolver.resolve();
    }

    @SuppressWarnings("unchecked")
    public <T> T getByPath(String path, Class<T> cls) {
        if (path != null && !path.isEmpty() && topJsonValue != null) {
            JsonValue jsonValue = this.topJsonValue;
            String[] paths = path.split("/");
            for (String s : paths) {
                s = s.trim();
                if (s.isEmpty()) continue;
                if (s.contains("[") && s.contains("]")) {
                    // array
                    int i1 = s.indexOf("["), i2 = s.indexOf("]", i1);
                    if (i1 > -1 && i2 > -1) {
                        int index;
                        try {
                            index = Integer.parseInt(s.substring(i1 + 1, i2));
                        } catch (NumberFormatException e) {
                            jsonValue = null;
                            break;
                        }
                        String key = s.substring(0, i1);
                        if (!key.isEmpty()) {
                            if (jsonValue instanceof JsonObject jsonObject) {
                                jsonValue = jsonObject.valueMap.get(key);
                                if (jsonValue instanceof JsonArray jsonArray && index >= 0 && index < jsonArray.getSize()) {
                                    jsonValue = jsonArray.valueList.get(index);
                                }
                            }
                        }
                    }
                } else if (jsonValue instanceof JsonObject jsonObject) {
                    // object
                    jsonValue = jsonObject.valueMap.get(s);
                } else {
                    jsonValue = null;
                }
                if (jsonValue == null) {
                    break;
                }
            }
            if (jsonValue != null) {
                try {
                    Object value = jsonValue.getValue(cls);
                    return (T) value;
                } catch (JSONParseException e) {
                    SysLogUtil.logNormalLn("origin json string: " + this.source);
                    throw e;
                }
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.source;
    }
}
