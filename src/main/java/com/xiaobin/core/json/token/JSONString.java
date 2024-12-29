package com.xiaobin.core.json.token;

import com.xiaobin.core.json.exception.JSONParseException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * created by xuweibin at 2024/12/10 17:27
 */
public class JSONString extends JSONToken<String> {

    public JSONString(String value) {
        super(value);
    }

    @Override
    public Object evaluate(Class<?> cls) {
        if (super.value != null) {
            if (int.class.isAssignableFrom(cls) || Integer.class.isAssignableFrom(cls)) {
                return Integer.parseInt(super.value);
            } else if (byte.class.isAssignableFrom(cls) || Byte.class.isAssignableFrom(cls)) {
                return Byte.parseByte(super.value);
            } else if (short.class.isAssignableFrom(cls) || Short.class.isAssignableFrom(cls)) {
                return Short.parseShort(super.value);
            } else if (long.class.isAssignableFrom(cls) || Long.class.isAssignableFrom(cls)) {
                return Long.parseLong(super.value);
            } else if (float.class.isAssignableFrom(cls) || Float.class.isAssignableFrom(cls)) {
                System.out.println("建议使用java.math.BigDecimal替换");
                return Float.parseFloat(super.value);
            } else if (double.class.isAssignableFrom(cls) || Double.class.isAssignableFrom(cls)) {
                System.out.println("建议使用java.math.BigDecimal替换");
                return Double.parseDouble(super.value);
            } else if (BigDecimal.class.isAssignableFrom(cls)) {
                return new BigDecimal(super.value);
            } else if (BigInteger.class.isAssignableFrom(cls)) {
                return new BigInteger(super.value);
            } else if (LocalDate.class.isAssignableFrom(cls)) {
                return LocalDate.parse(super.value, DateTimeFormatter.ISO_DATE);
            } else if (LocalDateTime.class.isAssignableFrom(cls)) {
                return LocalDateTime.parse(super.value, DATE_TIME_FORMATTER);
            } else if (Enum.class.isAssignableFrom(cls)) {
                try {
                    Object values = cls.getDeclaredMethod("values").invoke(null);
                    if (values instanceof Object[] valArr) {
                        for (Object val : valArr) {
                            if (val != null) {
                                if (val.toString().equals(super.value)) {
                                    return val;
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new JSONParseException("json number can't convert to " + cls);
        }
        return null;
    }
}
