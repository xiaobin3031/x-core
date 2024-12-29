package com.xiaobin.core.json.token;

/**
 * created by xuweibin at 2024/12/10 17:28
 */
public class JSONLiteral extends JSONToken<JSONLiteral.Value> {

    public final static JSONLiteral TRUE = new JSONLiteral(Value.TRUE);
    public final static JSONLiteral FALSE = new JSONLiteral(Value.FALSE);
    public final static JSONLiteral NULL = new JSONLiteral(Value.NULL);

    private JSONLiteral(Value value) {
        super(value);
    }

    @Override
    public Object evaluate(Class<?> cls) {
        if (super.value != null) {
            if (cls.isAssignableFrom(boolean.class) || cls.isAssignableFrom(Boolean.class)) {
                return switch (super.value) {
                    case TRUE -> true;
                    case FALSE -> false;
                    default -> null;
                };
            } else if (cls.isAssignableFrom(String.class)) {
                return switch (super.value) {
                    case TRUE -> "true";
                    case FALSE -> "false";
                    default -> null;
                };
            }
        }
        return null;
    }

    public enum Value {
        TRUE, FALSE, NULL
    }
}
