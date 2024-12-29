package com.xiaobin.core.json.token;

import com.xiaobin.core.bean.BeanManager;

import java.util.Map;

/**
 * created by xuweibin at 2024/12/10 17:27
 */
public class JSONObject extends JSONToken<Map<String, Object>> {

    public JSONObject(Map<String, Object> value) {
        super(value);
    }

    @Override
    public Object evaluate(Class<?> cls) {
        if (super.value != null) {
            BeanManager<?> beanManager = new BeanManager<>(cls);
            Object instance = beanManager.newInstance();
            for (Map.Entry<String, Object> entry : super.value.entrySet()) {
                beanManager.fillBean(instance, entry.getKey(), entry.getValue());
            }
            return instance;
        }

        return null;
    }
}
