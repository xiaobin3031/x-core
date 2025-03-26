package com.xiaobin.core.json.path;

/**
 * created by xuweibin at 2025/3/26
 */
class Token {

    public static final Token EOF = new Token(TokenType.EOF, null);
    public static final Token TRUE = new Token(TokenType.TRUE, true);
    public static final Token FALSE = new Token(TokenType.FALSE, false);
    public static final Token NULL = new Token(TokenType.NULL, null);

    TokenType type;

    Object value;

    Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }
}
