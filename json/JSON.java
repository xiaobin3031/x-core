package com.xiaobin.core.json;

import com.xiaobin.core.bean.BeanManager;
import com.xiaobin.core.json.exception.JSONParseException;
import com.xiaobin.core.json.exception.JSONUnExceptCharacterException;
import com.xiaobin.core.json.model.FieldMethodModel;

import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by XWB on 2024-08-03.
 *
 * @see <a href="https://www.json.org/json-en.html">json</a>
 * <p>
 * 这个工具只对内部使用，所以某些转换只会有特定的格式
 * 比如
 * LocalDate -> String   "yyyy-MM-dd"
 * LocalDateTime -> String    "yyyy-MM-dd HH:mm:ss"
 */
public class JSON {

    private final static Map<String, Map<String, FieldMethodModel>> CLASS_FIELD_METHOD_MAP = new HashMap<>();
    private final static char COLON = ':';
    private final static char COMMA = ',';
    private final static char BEGIN_ARRAY = '[';
    private final static char END_ARRAY = ']';
    private final static char BEGIN_OBJECT = '{';
    private final static char END_OBJECT = '}';
    private final static int SIGN_PLUS = '+';
    private final static int SIGN_MINUS = '-';
    private final static int DIGIT_POINT = '.';
    private final static char DOUBLE_QUOTA = '"';
    private final static int EOF = -1;
    private final static byte[] TRUE = new byte[]{'t', 'r', 'u', 'e'};
    private final static byte[] FALSE = new byte[]{'f', 'a', 'l', 's', 'e'};
    private final static byte[] NULL = new byte[]{'n', 'u', 'l', 'l'};
    private final static byte[] BYTES4 = new byte[4];
    private final static byte[] BYTES5 = new byte[5];
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private PushbackInputStream pushbackInputStream;
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    // fixme 先做个测试
    private char[] chars = new char[204800];
    private int charPos = 0;

    public JSON withSource(String source) {
        this.pushbackInputStream = new PushbackInputStream(new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)));
        return this;
    }

    public JSON withSource(InputStream inputStream) {
        this.pushbackInputStream = new PushbackInputStream(inputStream);
        return this;
    }

    private String readNumber() {
        this.skipWs();

        this.byteArrayOutputStream.reset();
        int b = this.read();
        if (b == SIGN_MINUS || b == SIGN_PLUS) {
            this.byteArrayOutputStream.write(b);
            b = this.read();
        }

        if (b == '0') {
            b = this.read();
            if (b == 'x' || b == 'X') {
                // hex
                while (true) {
                    b = this.read();
                    if (this.isHex(b)) {
                        this.byteArrayOutputStream.write(b);
                    } else {
                        this.unread(b);
                        break;
                    }
                }
                return String.valueOf(Integer.parseInt(this.byteArrayOutputStream.toString(StandardCharsets.UTF_8), 16));
            } else if (b == 'b' || b == 'B') {
                // binary
                while (true) {
                    b = this.read();
                    if (b == '1' || b == '0') {
                        this.byteArrayOutputStream.write(b);
                    } else {
                        this.unread(b);
                        break;
                    }
                }
                return String.valueOf(Integer.parseInt(this.byteArrayOutputStream.toString(StandardCharsets.UTF_8), 2));
            } else if (this.isDigit(b)) {
                this.byteArrayOutputStream.write(b);
                // octal
                this.readDigit();
                return String.valueOf(Integer.parseInt(this.byteArrayOutputStream.toString(StandardCharsets.UTF_8), 8));
            } else if (b == DIGIT_POINT) {
                // decimal
                this.byteArrayOutputStream.write(b);
                b = this.read();
                if (!this.isDigit(b)) {
                    throw new JSONParseException("except character 0-9, but get " + (char) b);
                }
                this.byteArrayOutputStream.write(b);
                this.readDigit();
            } else {
                // 只是单纯的读个0
                this.byteArrayOutputStream.write('0');
                this.unread(b);
            }
        } else if (this.isDigitOneNine(b)) {
            this.byteArrayOutputStream.write(b);
            this.readDigit();
            b = this.read();
            if (b == '.') {
                this.byteArrayOutputStream.write(b);
                b = this.read();
                if (!this.isDigit(b)) {
                    throw new JSONParseException("except character 0-9, but get " + (char) b);
                }
                this.byteArrayOutputStream.write(b);
                this.readDigit();
            } else {
                this.unread(b);
            }
            this.readExponent();
        } else {
            throw new JSONParseException("read number error");
        }

        this.skipWs();
        byte[] bytes = this.byteArrayOutputStream.toByteArray();
        if (bytes.length == 0) {
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private void readDigit() {
        int b;
        while (true) {
            b = this.read();
            if (this.isDigit(b)) {
                this.byteArrayOutputStream.write(b);
            } else {
                this.unread(b);
                break;
            }
        }
    }

    private void readExponent() {
        int b = this.read();
        if (b == 'e' || b == 'E') {
            this.byteArrayOutputStream.write(b);
            b = this.read();
            if (b == SIGN_MINUS || b == SIGN_PLUS) {
                this.byteArrayOutputStream.write(b);
                b = this.read();
                if (!this.isDigit(b)) {
                    throw new JSONParseException("except digit, but get " + (char) b);
                }
                this.byteArrayOutputStream.write(b);
                this.readDigit();
            } else if (!this.isDigit(b)) {
                this.byteArrayOutputStream.write(b);
                this.readDigit();
            } else {
                this.unread(b);
            }
        } else {
            this.unread(b);
        }
    }

    private Object convertValue(Object value, Class<?> cls) {
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
                        System.out.println("建议使用java.math.BigDecimal替换");
                        return Float.parseFloat(string);
                    } else if (double.class.isAssignableFrom(cls) || Double.class.isAssignableFrom(cls)) {
                        System.out.println("建议使用java.math.BigDecimal替换");
                        return Double.parseDouble(string);
                    } else if (BigDecimal.class.isAssignableFrom(cls)) {
                        return new BigDecimal(string);
                    } else if (BigInteger.class.isAssignableFrom(cls)) {
                        return new BigInteger(string);
                    }
                } catch (Exception e) {
                    throw new JSONParseException("convert number: " + string + " to " + cls + " error!");
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
            } else if (String.class.isAssignableFrom(cls)) {
                value = String.valueOf(value);
            } else if (Set.class.isAssignableFrom(cls)) {
                if (!(value instanceof Set<?>)) {
                    if (value instanceof Collection<?> col) {
                        return new HashSet<>(col);
                    } else {
                        throw new JSONParseException("except java type of Set, actual " + cls);
                    }
                }
            }
        }

        return value;
    }

    private String readString() {
        this.skipWs();
        int b = this.read();
        if (b != DOUBLE_QUOTA) {
            throw new JSONUnExceptCharacterException('"', b);
        }

        this.byteArrayOutputStream.reset();
        while ((b = this.read()) != -1) {
            if (b == '\\') {
                int next = this.read();
                switch (next) {
                    case DOUBLE_QUOTA, '\\', '/', 'b', 'f', 'n', 'r', 't' -> this.byteArrayOutputStream.write(next);
                    case 'u' -> {
                        byte[] hex = new byte[6];
                        if (this.read(hex, 2, 4) == -1) {
                            throw new JSONParseException("read hex array error");
                        }
                        this.byteArrayOutputStream.write(next);
                        for (byte bb : hex) {
                            if (!this.isHex(bb)) {
                                throw new JSONParseException("not hex character, " + (char) bb);
                            }
                            this.byteArrayOutputStream.write(bb);
                        }
                    }
                    default -> throw new JSONParseException("unknown string point code");
                }
            } else if (this.isStringPointCode(b)) {
                this.byteArrayOutputStream.write(b);
            } else if (b == DOUBLE_QUOTA) {
                break;
            } else {
                throw new JSONParseException("unknown string point code");
            }
        }
        if (b == -1) {
            throw new JSONParseException("un excepted eof");
        }
        this.skipWs();
        return this.byteArrayOutputStream.toString(StandardCharsets.UTF_8);
    }

    public <T> T readObject(JavaType<T> javaType) {
        return this.readObjectInner(javaType);
    }

    public <T> T readObject(Class<T> cls) {
        return this.readObjectInner(cls);
    }

    public <T> List<T> readList(Class<T> cls) {
        return this.readListInner(cls);
    }

    public <T> List<T> readList(JavaType<T> cls) {
        return this.readListInner(cls);
    }

    private Object readValue(Field field) {
        Object value = null;
        int b = this.read();
        if (b == DOUBLE_QUOTA) {
            // string
            this.unread(b);
            value = this.readString();
        } else if (b == BEGIN_OBJECT) {
            // object
            this.unread(b);
            if (field == null) {
                this.skipObject();
            } else {
                Class<?> fieldType = field.getType();
                if (!fieldType.isPrimitive() && !Collection.class.isAssignableFrom(fieldType)) {
                    if (Map.class.isAssignableFrom(fieldType)) {
                        Class<?> keyCls;
                        Type valCls;
                        Type[] subTypes = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                        if (subTypes.length == 0) {
                            keyCls = Object.class;
                            valCls = Object.class;
                        } else {
                            keyCls = (Class<?>) subTypes[0];
                            valCls = subTypes[1];
                        }
                        value = this.readMap(keyCls, valCls);
                    } else {
                        // object
                        value = this.readObjectInner(fieldType);
                    }
                }
            }
        } else if (b == BEGIN_ARRAY) {
            // array
            this.unread(b);
            if (field == null) {
                this.skipCollection();
            } else {
                Class<?> fieldType = field.getType();
                if (Collection.class.isAssignableFrom(fieldType)) {
                    Type genericType = field.getGenericType();
                    Class<?> collectionClazz;
                    if (genericType instanceof ParameterizedType aa) {
                        collectionClazz = (Class<?>) aa.getActualTypeArguments()[0];
                    } else {
                        collectionClazz = (Class<?>) genericType;
                    }
                    value = this.readListInner(collectionClazz);
                } else {
                    throw new JSONParseException("except Collection<?>, actual " + fieldType);
                }
            }
        } else if (b == SIGN_PLUS || b == SIGN_MINUS || this.isDigit(b)) {
            // number
            this.unread(b);
            value = this.readNumber();
        } else if (b == TRUE[0]) {
            this.unread(b);
            this.checkTrue();
            if (field != null) {
                value = Boolean.TRUE;
            }
        } else if (b == FALSE[0]) {
            // maybe false
            this.unread(b);
            this.checkFalse();
            if (field != null) {
                value = Boolean.FALSE;
            }
        } else if (b == NULL[0]) {
            // maybe null
            this.unread(b);
            this.checkNull();
        } else {
            throw new JSONParseException("unknown json value: " + (char) b);
        }
        if (field != null && Modifier.isTransient(field.getModifiers())) {
            return null;
        }
        return this.convertValue(value, field == null ? null : field.getType());
    }

    @SuppressWarnings("unchecked")
    private <T> T readRecordInner(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> min = null;
        for (Constructor<?> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            if (parameters.length == 0) {
                min = constructor;
                break;
            }
            if (min == null) {
                min = constructor;
            } else if (min.getParameters().length > parameters.length) {
                min = constructor;
            }
        }
        if (min == null) {
            return null;
        }

        Parameter[] parameters = min.getParameters();
        if (parameters.length == 0) {
            this.skipObject();
            try {
                return (T) min.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        }

        Object[] values = new Object[parameters.length];

        int b = this.read();
        if (b != BEGIN_OBJECT) {
            throw new JSONUnExceptCharacterException(BEGIN_OBJECT, b);
        }

        this.skipWs();

        boolean readedFlag = false;
        Object value;
        b = this.read();
        if (b != END_OBJECT) {
            // 空对象
            this.unread(b);
            while (true) {
                String key = this.readString();
                if (key.isEmpty()) {
                    break;
                }
                int index = -1;
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].getName().equals(key)) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    continue;
                }

                // value
                b = this.read();
                if (b == COLON) {
                    this.skipWs();
                    value = this.readValue(parameters[index].getType());
                } else {
                    throw new JSONUnExceptCharacterException(COLON, b);
                }
                values[index] = value;

                this.skipWs();
                b = this.read();
                if (b == COMMA) {
                    this.skipWs();
                } else if (b == END_OBJECT) {
                    break;
                }

                readedFlag = true;
            }
        }

        if (b != END_OBJECT) {
            throw new JSONUnExceptCharacterException(END_OBJECT, b);
        }
        this.skipWs();

        if (readedFlag) {
            try {
                return (T) min.newInstance(values);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            }
        }

        return null;
    }

    private <T> T readObjectInner(Class<T> clazz) {
        if (clazz.getSuperclass() != null && Record.class.isAssignableFrom(clazz.getSuperclass())) {
            return this.readRecordInner(clazz);
        }
        this.skipWs();
        int b = this.read();
        if (b != BEGIN_OBJECT) {
            throw new JSONUnExceptCharacterException(BEGIN_OBJECT, b);
        }

        this.skipWs();

        T t = null;
        Object value;
        b = this.read();
        if (b != END_OBJECT) {
            // 空对象
            this.unread(b);
            t = this.getInstance(clazz);
            while (true) {
                String key = this.readString();
                if (key.isEmpty()) {
                    break;
                }

                // value
                b = this.read();
                Field field;
                try {
                    field = clazz.getDeclaredField(key);
                } catch (NoSuchFieldException e) {
                    field = null;
                }
                if (b == COLON) {
                    this.skipWs();
                    value = this.readValue(field);
                } else {
                    throw new JSONUnExceptCharacterException(COLON, b);
                }

                if (field != null && value != null) {
                    this.setObjectValue(t, clazz, field.getType(), key, value);
                }

                this.skipWs();
                b = this.read();
                if (b == COMMA) {
                    this.skipWs();
                } else if (b == END_OBJECT) {
                    break;
                }
            }
        }

        if (b != END_OBJECT) {
            throw new JSONUnExceptCharacterException(END_OBJECT, b);
        }
        this.skipWs();

        return t;
    }

    @SuppressWarnings("unchecked")
    private <T> T readObjectInner(JavaType<T> javaType) {
        Class<?> clazz = javaType.getType();
        if (clazz.isAssignableFrom(Map.class)) {
            Class<?> keyCls, valCls;
            Type[] types = javaType.getActualArgs();
            if (types.length == 0) {
                keyCls = Object.class;
                valCls = Object.class;
            } else {
                keyCls = (Class<?>) types[0];
                valCls = (Class<?>) types[1];
            }
            return (T) this.readMap(keyCls, valCls);
        }
        return (T) this.readObjectInner(clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> readListInner(JavaType<T> javaType) {
        return (List<T>) this.readListInner(javaType.getType());
    }

    private Map<Object, Object> readMap(Class<?> keyCls, Type valueCls) {
        Map<Object, Object> map = new HashMap<>();
        int b;
        Object value;
        Object key;

        this.skipWs();
        b = this.read();
        if (b == BEGIN_OBJECT) {
            b = this.read();
            if (b != END_OBJECT) {
                this.unread(b);
                while (true) {
                    this.skipWs();
                    key = this.readValue(keyCls);
                    if (key != null) {
                        this.skipWs();
                        b = this.read();
                        if (b == COLON) {
                            this.skipWs();
                            value = this.readValue(valueCls);
                            map.put(key, value);
                        } else {
                            throw new JSONUnExceptCharacterException(COLON, b);
                        }

                        b = this.read();
                        if (b == COMMA) {
                            this.skipWs();
                            continue;
                        } else if (b == END_OBJECT) {
                            break;
                        } else if (b == EOF) {
                            throw new JSONParseException("eof");
                        }
                    }
                    // key是空的，或者都读完了，退出
                    break;
                }
            }
        } else {
            throw new JSONUnExceptCharacterException(BEGIN_OBJECT, b);
        }
        return map;
    }

    private Object readValue(Type type) {
        Object value = null;
        int b = this.read();
        if (b == DOUBLE_QUOTA) {
            // string
            this.unread(b);
            value = this.readString();
        } else if (b == BEGIN_OBJECT) {
            // object
            this.unread(b);
            Class<?> cls;
            if (type instanceof Class<?> clazz) {
                cls = clazz;
            } else if (type instanceof ParameterizedType parameterizedType) {
                cls = (Class<?>) parameterizedType.getRawType();
            } else {
                this.skipObject();
                return null;
            }
            if (!cls.isPrimitive() && !Collection.class.isAssignableFrom(cls)) {
                if (Map.class.isAssignableFrom(cls)) {
                    Class<?> keyCls = null;
                    Type valCls = null;
                    if (type instanceof ParameterizedType pa) {
                        Type[] types = pa.getActualTypeArguments();
                        if (types.length != 0) {
                            keyCls = (Class<?>) types[0];
                            valCls = types[1];
                        }
                    } else {
                        Type subType = cls.getGenericSuperclass();
                        if (subType instanceof ParameterizedType pa) {
                            Type[] types = pa.getActualTypeArguments();
                            if (types.length != 0) {
                                keyCls = (Class<?>) types[0];
                                valCls = types[1];
                            }
                        }
                    }
                    if (keyCls == null) keyCls = Object.class;
                    if (valCls == null) valCls = Object.class;
                    value = this.readMap(keyCls, valCls);
                } else {
                    // object
                    value = this.readObjectInner(cls);
                }
            }
        } else if (b == BEGIN_ARRAY) {
            // array
            this.unread(b);
            Class<?> cls = type.getClass();
            if (Collection.class.isAssignableFrom(cls)) {
                Type genericType = cls.getGenericSuperclass();
                Class<?> collectionClazz;
                if (genericType instanceof ParameterizedType aa) {
                    collectionClazz = (Class<?>) aa.getActualTypeArguments()[0];
                } else {
                    collectionClazz = (Class<?>) genericType;
                }
                value = this.readListInner(collectionClazz);
            } else {
                value = this.readListInner(Object.class);
            }
        } else if (b == SIGN_PLUS || b == SIGN_MINUS || this.isDigit(b)) {
            // number
            this.unread(b);
            value = this.readNumber();
        } else if (b == TRUE[0]) {
            this.unread(b);
            this.checkTrue();
            value = Boolean.TRUE;
        } else if (b == FALSE[0]) {
            // maybe false
            this.unread(b);
            this.checkFalse();
            value = Boolean.FALSE;
        } else if (b == NULL[0]) {
            // maybe null
            this.unread(b);
            this.checkNull();
        } else {
            throw new JSONParseException("unknown json value: " + (char) b);
        }
        return this.convertValue(value, type.getClass());
    }

    private <T> List<T> readListInner(Class<T> clazz) {
        int b = this.read();
        if (b != BEGIN_ARRAY) {
            throw new JSONUnExceptCharacterException(BEGIN_ARRAY, b);
        }
        int firstElement = 0;
        List<T> list = new ArrayList<>();

        b = this.read();
        if (b != END_ARRAY) {
            this.unread(b);
            Object value;
            while (true) {
                this.skipWs();
                b = this.read();
                this.unread(b);
                value = null;
                if (b == DOUBLE_QUOTA) {
                    // string
                    firstElement = this.checkAndGetElementType(firstElement, 1);
                    value = this.readString();
                } else if (b == BEGIN_OBJECT) {
                    // object
                    firstElement = this.checkAndGetElementType(firstElement, 2);
                    if (BeanManager.isObjectCls(clazz)) {
                        value = this.<Map<String, Object>>readObjectInner(new JavaType<>() {
                        });
                    } else {
                        value = this.readObjectInner(clazz);
                    }
                } else if (b == BEGIN_ARRAY) {
                    // array
                    firstElement = this.checkAndGetElementType(firstElement, 3);
                    value = this.readListInner(clazz);
                } else if (b == SIGN_PLUS || b == SIGN_MINUS || this.isDigit(b)) {
                    // number
                    firstElement = this.checkAndGetElementType(firstElement, 4);
                    value = this.readNumber();
                } else if (b == TRUE[0]) {
                    firstElement = this.checkAndGetElementType(firstElement, 5);
                    this.checkTrue();
                    value = true;
                } else if (b == FALSE[0]) {
                    firstElement = this.checkAndGetElementType(firstElement, 5);
                    this.checkFalse();
                    value = false;
                } else if (b == NULL[0]) {
                    this.checkNull();
                } else {
                    throw new JSONParseException("unknown json value: " + (char) b);
                }
                if (value != null) {
                    list.add((T) this.convertValue(value, clazz));
                }
                this.skipWs();
                b = this.read();
                if (b == END_ARRAY) {
                    // end
                    break;
                } else if (b == COMMA) {
                    // next
                    this.skipWs();
                } else if (b == EOF) {
                    throw new JSONParseException("eof");
                }
            }
        }

        return list;
    }

    private int checkAndGetElementType(int elementType, int exceptType) {
        if (elementType != 0 && elementType != exceptType) {
            throw new JSONParseException("类型不一致");
        }
        return exceptType;
    }

    private void checkTrue() {
        Arrays.fill(BYTES4, (byte) 0);
        if (this.read(BYTES4, 0, BYTES4.length) == EOF) {
            throw new JSONParseException("eof");
        }
        if (!Arrays.equals(BYTES4, TRUE)) {
            throw new JSONParseException("except true");
        }
    }

    private void checkFalse() {
        Arrays.fill(BYTES5, (byte) 0);
        if (this.read(BYTES5, 0, BYTES5.length) == EOF) {
            throw new JSONParseException("eof");
        }
        if (!Arrays.equals(BYTES5, FALSE)) {
            throw new JSONParseException("except false");
        }
    }

    private void checkNull() {
        byte[] bytes = new byte[4];
        if (this.read(bytes, 0, bytes.length) == EOF) {
            throw new JSONParseException("eof");
        }
        if (!Arrays.equals(bytes, NULL)) {
            throw new JSONParseException("except null");
        }
    }

    private void skipObject() {
        int b = this.read();
        if (b != BEGIN_OBJECT) {
            throw new JSONUnExceptCharacterException(BEGIN_OBJECT, b);
        }
        int count = 1;
        while (true) {
            b = this.read();
            if (b == '\\') {
                this.read();
            } else if (b == BEGIN_OBJECT) {
                count++;
            } else if (b == END_OBJECT) {
                count--;
                if (count == 0) {
                    break;
                }
            } else if (b == EOF) {
                break;
            }
        }

        if (b != END_OBJECT) {
            throw new JSONUnExceptCharacterException(END_OBJECT, b);
        }
    }

    private void skipCollection() {
        int b = this.read();
        if (b != BEGIN_ARRAY) {
            throw new JSONUnExceptCharacterException(BEGIN_ARRAY, b);
        }

        int count = 1;
        while (true) {
            b = this.read();
            if (b == '\\') {
                this.read();
            } else if (b == BEGIN_ARRAY) {
                count++;
            } else if (b == END_ARRAY) {
                count--;
                if (count == 0) break;
            } else if (b == EOF) {
                break;
            }
        }
        if (b != END_ARRAY) {
            throw new JSONUnExceptCharacterException(END_ARRAY, b);
        }
    }

    private void skipWs() {
        int b;
        while ((b = this.read()) != EOF) {
            if (!this.isWhite(b)) {
                this.unread(b);
                break;
            }
        }
    }

    private int read() {
        try {
            return this.pushbackInputStream.read();
        } catch (IOException e) {
            return EOF;
        }
    }

    private int read(byte[] bytes, int offset, int len) {
        try {
            return this.pushbackInputStream.read(bytes, offset, len);
        } catch (IOException e) {
            return EOF;
        }
    }

    private void unread(int b) {
        try {
            this.pushbackInputStream.unread(b);
        } catch (IOException e) {
            throw new JSONParseException("unread error: " + e.getMessage(), e);
        }
    }

    private boolean isWhite(int b) {
        return b == ' ' || b == '\r' || b == '\n' || b == '\t';
    }

    private boolean isHex(int b) {
        return this.isDigit(b) || b >= 'a' && b <= 'f' || b >= 'A' && b <= 'F';
    }

    private boolean isDigit(int b) {
        return b == '0' || isDigitOneNine(b);
    }

    private boolean isDigitOneNine(int b) {
        return b >= '1' && b <= '9';
    }

    private boolean isStringPointCode(int b) {
        return b >= 0x20 && b <= 0x10ffff && b != DOUBLE_QUOTA;
    }

//    private boolean isNumberElement(Class<?> cls) {
//        return int.class.isAssignableFrom(cls) || Integer.class.isAssignableFrom(cls)
//                || short.class.isAssignableFrom(cls) || Short.class.isAssignableFrom(cls)
//                || byte.class.isAssignableFrom(cls) || Byte.class.isAssignableFrom(cls)
//                || long.class.isAssignableFrom(cls) || Long.class.isAssignableFrom(cls)
//                || double.class.isAssignableFrom(cls) || Double.class.isAssignableFrom(cls)
//                || float.class.isAssignableFrom(cls) || Float.class.isAssignableFrom(cls)
//                || boolean.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls)
//                || BigDecimal.class.isAssignableFrom(cls)
//                || BigInteger.class.isAssignableFrom(cls);
//    }

    private <T> void setObjectValue(T t, Class<?> clazz, Class<?> fieldType, String fieldName, Object... value) {
        try {
            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = clazz.getDeclaredMethod(methodName, fieldType);
            method.invoke(t, value);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new JSONParseException("new instance error: " + e.getMessage(), e);
        }
    }

    public String parse(Object object) {
        this.charPos = 0;
        this.parseValue(object);
        if (this.charPos == 0) {
            return null;
        }
        return new String(this.chars, 0, this.charPos);
    }

    private Map<String, FieldMethodModel> loadFieldMethodCache(Class<?> cls) {
        Map<String, FieldMethodModel> fieldMethodMap = CLASS_FIELD_METHOD_MAP.get(cls.getName());
        if (fieldMethodMap == null) {
            fieldMethodMap = this.syncLoadFieldMethodCache(cls);
            CLASS_FIELD_METHOD_MAP.put(cls.getName(), fieldMethodMap);
        }

        return fieldMethodMap;
    }

    private synchronized Map<String, FieldMethodModel> syncLoadFieldMethodCache(Class<?> cls) {
        Map<String, FieldMethodModel> fieldMethodMap = new HashMap<>();
        Field[] fields = cls.getDeclaredFields();
        Method setMethod, getMethod;
        Method[] methods = cls.getDeclaredMethods();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            methodMap.put(method.getName(), method);
        }
        for (Field field : fields) {
            if (!Modifier.isTransient(field.getModifiers())) {
                String name = field.getName();
                if (field.getName().length() == 1) {
                    name = name.toUpperCase();
                } else {
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                }

                setMethod = methodMap.getOrDefault("set" + name, methodMap.get(field.getName()));
                getMethod = methodMap.getOrDefault("get" + name, methodMap.getOrDefault("is" + name, methodMap.get(field.getName())));
                if (setMethod == null && getMethod == null) continue;

                FieldMethodModel model = new FieldMethodModel();
                model.setField(field);
                model.setGetMethod(getMethod);
                model.setSetMethod(setMethod);

                fieldMethodMap.put(field.getName(), model);
            }
        }

        return fieldMethodMap;
    }

    private void parseValue(Object value) {
        if (value == null) {
            return;
        }
        if (value.getClass().isPrimitive() || value instanceof Number || value instanceof Character || value instanceof Boolean) {
            this.insertStr(String.valueOf(value));
        } else if (value instanceof LocalDateTime time) {
            this.insertLocalDateTime(time);
        } else if (value instanceof LocalDate date) {
            this.insertLocalDate(date);
        } else if (value instanceof String str) {
            this.insertRealStr(str);
        } else if (value instanceof Enum<?>) {
            this.insertRealStr(value.toString());
        } else if (value instanceof Collection<?> list) {
            this.parseCollection(list);
        } else if (value instanceof Map<?, ?> map) {
            this.parseMap(map);
        } else {
            this.parseObject(value);
        }
    }

    private void insertLocalDate(LocalDate time) {
        this.chars[this.charPos++] = DOUBLE_QUOTA;
        this.insertYear(time.getYear());
        this.chars[this.charPos++] = '-';
        this.insertOtherTime(time.getMonthValue());
        this.chars[this.charPos++] = '-';
        this.insertOtherTime(time.getDayOfMonth());
        this.chars[this.charPos++] = DOUBLE_QUOTA;
    }

    private void insertLocalDateTime(LocalDateTime time) {
        this.chars[this.charPos++] = DOUBLE_QUOTA;
        this.insertYear(time.getYear());
        this.chars[this.charPos++] = '-';
        this.insertOtherTime(time.getMonthValue());
        this.chars[this.charPos++] = '-';
        this.insertOtherTime(time.getDayOfMonth());

        this.chars[this.charPos++] = ' ';

        this.insertOtherTime(time.getHour());
        this.chars[this.charPos++] = ':';
        this.insertOtherTime(time.getMinute());
        this.chars[this.charPos++] = ':';
        this.insertOtherTime(time.getSecond());
        this.chars[this.charPos++] = DOUBLE_QUOTA;
    }

    private void insertYear(int year) {
        this.charPos += 4;
        this.chars[this.charPos - 4] = (char) (year / 1000 + '0');
        this.chars[this.charPos - 3] = (char) (year / 100 % 10 + '0');
        this.chars[this.charPos - 2] = (char) (year / 10 % 10 + '0');
        this.chars[this.charPos - 1] = (char) (year % 10 + '0');
    }

    private void insertOtherTime(int time) {
        if (time < 10) {
            this.chars[this.charPos++] = '0';
            this.chars[this.charPos++] = (char) (time + '0');
        } else {
            this.chars[this.charPos++] = (char) (time / 10 + '0');
            this.chars[this.charPos++] = (char) (time % 10 + '0');
        }
    }

    private void insertStr(String str) {
        int len = str.length();
        str.getChars(0, len, this.chars, this.charPos);
        this.charPos += len;
    }

    private void insertRealStr(String str) {
        this.chars[this.charPos++] = DOUBLE_QUOTA;
        this.insertStr(str);
        this.chars[this.charPos++] = DOUBLE_QUOTA;
    }

    private void parseCollection(Collection<?> collection) {
        this.chars[this.charPos++] = BEGIN_ARRAY;
        if (collection != null && !collection.isEmpty()) {
            for (Object o : collection) {
                this.parseValue(o);
                this.chars[this.charPos++] = COMMA;
            }
            this.chars[this.charPos - 1] = END_ARRAY;
        } else {
            this.chars[this.charPos++] = END_ARRAY;
        }
    }

    private void parseMap(Map<?, ?> map) {
        this.chars[this.charPos++] = BEGIN_OBJECT;
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    this.insertRealStr(entry.getKey().toString());
                    this.chars[this.charPos++] = COLON;
                    this.parseValue(entry.getValue());
                    this.chars[this.charPos++] = COMMA;
                }
            }
            if (this.chars[this.charPos - 1] == COMMA) {
                this.chars[this.charPos - 1] = END_OBJECT;
            }
        } else {
            this.chars[this.charPos++] = END_OBJECT;
        }
    }

    private void parseObject(Object value) {
        this.chars[this.charPos++] = BEGIN_OBJECT;
        if (value != null) {
            Map<String, FieldMethodModel> fieldMethodMap = this.loadFieldMethodCache(value.getClass());
            if (!fieldMethodMap.isEmpty()) {
                Object subVal;
                for (Map.Entry<String, FieldMethodModel> entry : fieldMethodMap.entrySet()) {
                    Method getMethod = entry.getValue().getGetMethod();
                    if (getMethod != null) {
                        try {
                            subVal = getMethod.invoke(value);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            continue;
                        }
                        if (subVal != null) {
                            this.insertRealStr(entry.getKey());
                            this.chars[this.charPos++] = COLON;
                            this.parseValue(subVal);
                            this.chars[this.charPos++] = COMMA;
                        }
                    }
                }
            }
            if (this.chars[this.charPos - 1] == COMMA) {
                this.chars[this.charPos - 1] = END_OBJECT;
            }
        } else {
            this.chars[this.charPos++] = END_OBJECT;
        }
    }
}
