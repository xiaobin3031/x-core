package com.xiaobin.core.bean;

import com.xiaobin.core.bean.annotation.XBean;
import com.xiaobin.core.bean.model.BeanModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * created by xuweibin at 2024/8/26 17:57
 */
public class BeanManager<T> {

    private static final Map<String, List<BeanModel>> BEAN_MODEL_MAP = new HashMap<>();

    public BeanManager(Class<T> cls) {
        if (!BEAN_MODEL_MAP.containsKey(cls.getName())) {
            this.loadBeanModel(cls);
        }
    }

    private synchronized void loadBeanModel(Class<T> cls) {
        if (BEAN_MODEL_MAP.containsKey(cls.getName())) return;

        Method[] methods = cls.getDeclaredMethods();
        List<BeanModel> list = new ArrayList<>();
        for (Field field : cls.getDeclaredFields()) {
            BeanModel model = new BeanModel();
            model.setFieldName(field.getName());
            model.setGetMethod(this.getGetMethod(field.getName(), methods));
            model.setSetMethod(this.getSetMethod(field.getName(), methods));
            model.setField(field);

            XBean xBean = field.getDeclaredAnnotation(XBean.class);
            if (xBean != null) {
                model.setDescription(xBean.description());
                model.setDefaultValue(xBean.defaultValue());
                model.setOptional(xBean.optional());
                model.setIgnoreScene(xBean.ignoreScene());
            }
            list.add(model);
        }

        BEAN_MODEL_MAP.put(cls.getName(), list);
    }

    private Method getGetMethod(String fieldName, Method[] methods) {
        String name = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = this.getMethod(name, methods);
        if (method == null) {
            name = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            method = this.getMethod(name, methods);
        }
        return method;
    }

    private Method getSetMethod(String fieldName, Method[] methods) {
        String name = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return this.getMethod(name, methods);
    }

    private Method getMethod(String methodName, Method[] methods) {
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static boolean isObjectCls(Type type) {
        if (type instanceof Class<?> cls) {
            return cls.getName().equals("java.lang.Object");
        }
        return type.getClass().getName().equals("java.lang.Object");
    }
}
