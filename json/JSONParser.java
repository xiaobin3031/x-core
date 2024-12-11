package com.xiaobin.core.json;

import com.xiaobin.core.json.exception.JSONEofException;
import com.xiaobin.core.json.exception.JSONParseException;
import com.xiaobin.core.json.exception.JSONUnExceptCharacterException;
import com.xiaobin.core.json.token.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by xuweibin at 2024/12/3 13:17
 */
class JSONParser {

    private static final char KEY_VALUE_SPLIT = ':';
    private static final char VALUE_MULTI = ',';
    private static final char LEFT_BRACES = '{';
    private static final char RIGHT_BRACES = '}';
    private static final char LEFT_BRACKETS = '[';
    private static final char RIGHT_BRACKETS = ']';
    private static final char MINUS = '-';
    private static final char POINT = '.';

    private final StringBuilder stringValueBuilder = new StringBuilder(10);

    private final String content;
    private int index;

    public JSONParser(String content) {
        this.content = content;
        this.index = 0;
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> map = new HashMap<>();
        consume('{');
        this.skipWhiteSpace();
        if (checkIf('"')) {
            while (true) {
                String key = parseString();
                this.skipWhiteSpace();
                consume(':');
                JSONToken<?> value = this.parseValue();
                map.put(key, value);
                if (checkIf(',')) {
                    this.index++;
                } else {
                    break;
                }
            }
        }
        return map;
    }

    private JSONToken<List<JSONToken<?>>> parseArray() {
        List<JSONToken<?>> list = new ArrayList<>();
        consume('[');
        this.skipWhiteSpace();
        if (!checkIf(']')) {
            do {
                list.add(this.parseValue());
            } while (checkIf(','));
        }
        consume(']');
        return new JSONArray(list);
    }

    private JSONToken<?> parseValue() {
        this.skipWhiteSpace();
        JSONToken<?> value;
        if (checkIf('"')) {
            value = new JSONString(this.parseString());
        } else if (checkIf('-') || checkIfDigit()) {
            value = this.parseNumber();
        } else if (checkIf('{')) {
            value = new JSONObject(this.parseObject());
        } else if (checkIf('[')) {
            value = this.parseArray();
        } else if (checkIf('t')) {
            this.consume('r');
            this.consume('u');
            this.consume('e');
            value = JSONLiteral.TRUE;
        } else if (checkIf('f')) {
            this.consume('a');
            this.consume('l');
            this.consume('s');
            this.consume('e');
            value = JSONLiteral.FALSE;
        } else if (checkIf('n')) {
            this.consume('u');
            this.consume('l');
            this.consume('l');
            value = JSONLiteral.NULL;
        } else {
            throw new JSONParseException("not valid json value");
        }
        this.skipWhiteSpace();
        return value;
    }

    private String parseString() {
        consume('"');
        stringValueBuilder.setLength(0);
        while (this.peek() != '"') {
            stringValueBuilder.append(this.nextOne());
        }
        return stringValueBuilder.toString();
    }

    private JSONToken<String> parseNumber() {
        stringValueBuilder.setLength(0);
        if (checkIf('-')) {
            stringValueBuilder.append(this.nextOne());
        }
        if (checkIf('0')) {
            stringValueBuilder.append(this.nextOne());
        } else if (this.checkIfDigitNonZero()) {
            stringValueBuilder.append(this.nextOne());
            if (this.checkIfDigit()) {
                this.parseDigit();
            }
        } else {
            throw new JSONUnExceptCharacterException("[0-9]", this.nextOne(), this.index);
        }
        // fraction
        if (checkIf('.')) {
            stringValueBuilder.append(this.nextOne());
            this.parseDigit();
        }
        // exponent
        if (checkIf('e') || checkIf('E')) {
            stringValueBuilder.append(this.nextOne());
            if (checkIf('-') || checkIf('+')) {
                stringValueBuilder.append(this.nextOne());
            }
            this.parseDigit();
        }
        return new JSONString(stringValueBuilder.toString());
    }

    private void parseDigit() {
        char ch = this.nextOne();
        if (ch >= '0' && ch <= '9') {
            this.stringValueBuilder.append(ch);
            while (true) {
                ch = this.nextOne();
                if (ch >= '0' && ch <= '9') {
                    this.stringValueBuilder.append(ch);
                } else {
                    break;
                }
            }
        } else {
            throw new JSONUnExceptCharacterException("[0-9]", ch, this.index);
        }
    }

    private void consume(char except) {
        char actual = this.nextOne();
        if (actual != except) {
            throw new JSONUnExceptCharacterException(except, actual, this.index);
        }
    }

    private boolean checkIf(char realChar) {
        return this.peek() == realChar;
    }

    private boolean checkIfDigitNonZero() {
        char ch = this.peek();
        return ch >= '1' && ch <= '9';
    }

    private boolean checkIfDigit() {
        return this.peek() == '0' || this.checkIfDigitNonZero();
    }

    private char nextOne() {
        if (this.isEnd()) {
            throw new JSONEofException();
        }
        char ch = this.content.charAt(this.index);
        this.index++;
        return ch;
    }

    private char peek() {
        if (this.isEnd()) {
            throw new JSONEofException();
        }
        return this.content.charAt(this.index);
    }

    private boolean isEnd() {
        return index >= this.content.length();
    }

    private void skipWhiteSpace() {
        char ch;
        while (true) {
            ch = peek();
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n') {
                this.index++;
            } else {
                break;
            }
        }
    }
}
