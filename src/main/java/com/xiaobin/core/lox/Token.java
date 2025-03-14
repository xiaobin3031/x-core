package com.xiaobin.core.lox;

/**
 * created by xuweibin at 2024/12/18 17:10
 */
public class Token {

    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return this.type + " " + this.lexeme + " " + this.literal;
    }
}
