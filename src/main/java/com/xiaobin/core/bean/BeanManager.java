package com.xiaobin.core.bean;

import com.xiaobin.core.bean.annotation.XBean;
import com.xiaobin.core.bean.model.BeanModel;
import com.xiaobin.core.json.JSON;
import com.xiaobin.core.json.exception.JSONParseException;
import com.xiaobin.core.log.SysLogUtil;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * created by xuweibin at 2024/8/26 17:57
 */
public class BeanManager<T> {

    private static final Map<String, List<BeanModel>> BEAN_MODEL_MAP = new HashMap<>();
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Class<T> cls;
    private final List<BeanModel> beanModelList;
    private final Map<String, BeanModel> beanModelMap;

    public BeanManager(Class<T> cls) {
        if (!BEAN_MODEL_MAP.containsKey(cls.getName())) {
            this.loadBeanModel(cls);
        }
        this.beanModelList = BEAN_MODEL_MAP.get(cls.getName());
        this.cls = cls;
        this.beanModelMap = this.beanModelList.stream().collect(Collectors.toMap(BeanModel::getFieldName, Function.identity(), (k1, k2) -> k2));
    }

    private synchronized void loadBeanModel(Class<T> cls) {
        if (BEAN_MODEL_MAP.containsKey(cls.getName())) return;

        List<BeanModel> list = new ArrayList<>();
        Class<?> cc = cls;
        while (cc != null && !cc.getSimpleName().equals("java.lang.Object")) {
            Method[] methods = cc.getDeclaredMethods();
            for (Field field : cc.getDeclaredFields()) {
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
            cc = cc.getSuperclass();
        }

        BEAN_MODEL_MAP.put(cls.getName(), list);
    }

    public List<String> fieldsName() {
        return new ArrayList<>(this.beanModelMap.keySet());
    }

    public void fillBean(Object t, String fieldName, Object value) {
        Objects.requireNonNull(t);
        BeanModel beanModel = this.beanModelMap.get(fieldName);
        Objects.requireNonNull(beanModel, "unknown field name: " + fieldName);
        try {
            beanModel.getSetMethod().invoke(t, convertValue(value, beanModel.getField().getType()));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getVal(Object t, String fieldName) {
        BeanModel beanModel = this.beanModelMap.get(fieldName);
        if (beanModel != null && beanModel.getGetMethod() != null) {
            try {
                return beanModel.getGetMethod().invoke(t);
            } catch (IllegalAccessException | InvocationTargetException e) {
                SysLogUtil.logError(e.getMessage());
            }
        }
        return null;
    }

    public Field getField(String fieldName) {
        BeanModel beanModel = this.beanModelMap.get(fieldName);
        return beanModel == null ? null : beanModel.getField();
    }

    public static Object convertValue(Object value, Class<?> cls) {
        if (value != null && cls != null) {
            if (value instanceof String string) {
                // 数字
                try {
                    if (int.class.isAssignableFrom(cls) || Integer.class.isAssignableFrom(cls)) {
                        return Integer.parseInt(string);
                    } else if (byte.class.isAssignableFrom(cls) || Byte.class.isAssignableFrom(cls)) {
                        return Byte.parseByte(string);
                    } else if (short.class.isAssignableFrom(cls) || Short.class.isAssignableFrom(cls)) {
                        return Short.parseShort(string);
                    } else if (long.class.isAssignableFrom(cls) || Long.class.isAssignableFrom(cls)) {
                        return Long.parseLong(string);
                    } else if (float.class.isAssignableFrom(cls) || Float.class.isAssignableFrom(cls)) {
                        return Float.parseFloat(string);
                    } else if (double.class.isAssignableFrom(cls) || Double.class.isAssignableFrom(cls)) {
                        return Double.parseDouble(string);
                    } else if (BigDecimal.class.isAssignableFrom(cls)) {
                        return new BigDecimal(string);
                    } else if (BigInteger.class.isAssignableFrom(cls)) {
                        return new BigInteger(string);
                    }
                } catch (Exception e) {
                    throw new JSONParseException("convert number: " + string + " to " + cls + " error!");
                }

                if (Boolean.class.isAssignableFrom(cls)) {
                    try {
                        return Boolean.valueOf(string);
                    } catch (Exception e) {
                        return null;
                    }
                }

                if (boolean.class.isAssignableFrom(cls)) {
                    try {
                        return Boolean.valueOf(string);
                    } catch (Exception e) {
                        return false;
                    }
                }

                // 日期
                try {
                    if (LocalDate.class.isAssignableFrom(cls)) {
                        return LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
                    } else if (LocalDateTime.class.isAssignableFrom(cls)) {
                        return LocalDateTime.parse(string, DATE_TIME_FORMATTER);
                    }
                } catch (Exception e) {
                    throw new JSONParseException("convert date string: " + string + " to " + cls + " error!");
                }

                // enum
                try {
                    if (Enum.class.isAssignableFrom(cls)) {
                        Object values = cls.getDeclaredMethod("values").invoke(null);
                        if (values instanceof Object[] valArr) {
                            for (Object val : valArr) {
                                if (val != null) {
                                    if (val.toString().equals(value)) {
                                        value = val;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new JSONParseException("convert enum string: " + string + " to " + cls + " error!");
                }

                JSON json = new JSON();
                if (List.class.isAssignableFrom(cls)) {
                    value = json.withSource(string).readList(cls);
                } else if (Set.class.isAssignableFrom(cls)) {
                    value = json.withSource(string).readList(cls);
                    value = new HashSet<>((List<?>) value);
                } else if (Map.class.isAssignableFrom(cls)) {
                    value = json.withSource(string).readObject(cls);
                }
            } else {
                if (String.class.isAssignableFrom(cls)) {
                    value = String.valueOf(value);
                }
            }
        }

        return value;
    }

    public T newInstance() {
        try {
            Constructor<T> constructor = this.cls.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

    public static String objectToString(Object value) {
        if (value == null) return null;

        if (value instanceof Object[] os) {
            return Arrays.toString(os);
        } else if (value instanceof long[] os) {
            return Arrays.toString(os);
        } else if (value instanceof short[] os) {
            return Arrays.toString(os);
        } else if (value instanceof byte[] os) {
            return Arrays.toString(os);
        } else if (value instanceof char[] os) {
            return Arrays.toString(os);
        } else if (value instanceof double[] os) {
            return Arrays.toString(os);
        } else if (value instanceof float[] os) {
            return Arrays.toString(os);
        } else if (value instanceof boolean[] os) {
            return Arrays.toString(os);
        } else if (value instanceof int[] os) {
            return Arrays.toString(os);
        }

        return value.toString();
    }

    /**
     * 只匹配同类型的
     */
    public static boolean valueEqual(Object val1, Object val2) {
        try {
            if (val1 == null && val2 == null) {
                return true;
            } else if (val1 == null || val2 == null) {
                return false;
            } else if (!isPrimary(val1) && !isPrimary(val2) && !val1.getClass().getName().equals(val2.getClass().getName())) {
                return false;
            } else {
                if (isPrimary(val1)) {
                    return val1.toString().equals(val2.toString());
                } else if (val1 instanceof Comparable cb1) {
                    return cb1.compareTo(val2) == 0;
                } else if (val1.getClass().isPrimitive()) {
                    return val1 == val2;
                } else {
                    return val1.equals(val2);
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isPrimary(Object val1) {
        Class<?> aClass = val1.getClass();
        return aClass.isPrimitive()
                || aClass.isAssignableFrom(Integer.class)
                || aClass.isAssignableFrom(Long.class)
                || aClass.isAssignableFrom(Short.class)
                || aClass.isAssignableFrom(Byte.class)
                || aClass.isAssignableFrom(Character.class)
                || aClass.isAssignableFrom(Double.class)
                || aClass.isAssignableFrom(Float.class)
                || aClass.isAssignableFrom(Boolean.class);
    }

    public Map<String, Object> toMap(Object val) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, BeanModel> entry : beanModelMap.entrySet()) {
            Method getMethod = entry.getValue().getGetMethod();
            if (getMethod != null) {
                try {
                    Object invoke = getMethod.invoke(val);
                    if (invoke != null) {
                        result.put(entry.getKey(), invoke);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    SysLogUtil.logError(entry.getKey() + " get value error:" + e.getMessage());
                }
            }
        }
        return result;
    }
}
