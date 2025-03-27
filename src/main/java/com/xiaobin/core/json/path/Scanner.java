package com.xiaobin.core.json.path;

import com.xiaobin.core.json.exception.JSONParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * created by xuweibin at 2025/3/26
 */
public class Scanner {
    private final String jsonString;
    private final List<Token> tokenList = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int length;

    Scanner(String jsonString) {
        this.jsonString = jsonString;
        if (this.jsonString != null) {
            length = this.jsonString.length();
        }
    }

    private void scan() {
        char c = advance();
        switch (c) {
            case '{' -> addToken(TokenType.LEFT_BRACE, "{");
            case '}' -> addToken(TokenType.RIGHT_BRACE, "}");
            case ',' -> addToken(TokenType.COMMA, ",");
            case ':' -> addToken(TokenType.COLON, ":");
            case '[' -> addToken(TokenType.LEFT_SQUARE, "[");
            case ']' -> addToken(TokenType.RIGHT_SQUARE, "]");
            case '-' -> {
                if (isDigit(peekNext())) {
                    number();
                } else {
                    addToken(TokenType.MINUS, "-");
                }
            }
            case '+' -> addToken(TokenType.PLUS, "+");

            case '"' -> string();

            case ' ', '\n', '\r', '\t' -> {
            }

            default -> {
                if (isDigit(c)) {
                    number();
                } else if (c == 'f') {
                    literalFalse();
                } else if (c == 't') {
                    literalTrue();
                } else if (c == 'n') {
                    literalNull();
                } else {
                    throw new JSONParseException("unexpect char [" + c + "] at " + current);
                }
            }
        }
    }

    List<Token> scanToken() {
        while (!isAtEnd()) {
            start = current;
            scan();
        }
        addToken(TokenType.EOF, null);
        return tokenList;
    }

    private char advance() {
        current++;
        return this.jsonString.charAt(current - 1);
    }

    private char prev() {
        return this.jsonString.charAt(current - 1);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return this.jsonString.charAt(this.current);
    }

    private char peekNext() {
        if (current + 1 >= this.jsonString.length()) return '\0';
        return this.jsonString.charAt(current + 1);
    }

    private boolean match(char c) {
        if (isAtEnd()) return false;
        if (this.jsonString.charAt(current) != c) return false;

        current++;
        return true;
    }

    private boolean isAtEnd() {
        return current >= length;
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void string() {
        while (!isAtEnd() && peek() != '"') {
            char c = advance();
            // 转义的话，再读一个字符
            if (c == '\\') advance();
        }
        if (isAtEnd()) {
            throw new JSONParseException("unexpect end at " + (this.current - 1));
        }
        advance();
        addToken(TokenType.STRING, this.jsonString.substring(start + 1, current - 1));
    }

    private void number() {
        char first = prev();
        if (first != '0') {
            while (isDigit(peek())) advance();
        }
        if (peek() == '.' && isDigit(peekNext())) {
            do advance();
            while (isDigit(peek()));
        }
        if (peek() == 'e' || peek() == 'E') {
            char c = peekNext();
            if (c == '+' || c == '-' || isDigit(c)) {
                do advance();
                while (isDigit(peek()));
            }
        }

        addToken(TokenType.NUMBER, this.jsonString.substring(start, current));
    }

    private void literalFalse() {
        literal(new char[]{'a', 'l', 's', 'e'}, "false");
        addToken(TokenType.FALSE, false);
    }

    private void literalTrue() {
        literal(new char[]{'r', 'u', 'e'}, "true");
        addToken(TokenType.TRUE, true);
    }

    private void literalNull() {
        literal(new char[]{'u', 'l', 'l'}, "null");
        addToken(TokenType.NULL, null);
    }

    private void literal(char[] chars, String literal) {
        for (char c : chars) {
            if (!match(c))
                throw new JSONParseException("except character [" + c + "] of literal " + literal + " at: " + current);
        }
    }

    private void addToken(TokenType type, Object value) {
        tokenList.add(new Token(type, value));
    }
}
