package com.xiaobin.core.json.path;

import com.xiaobin.core.json.exception.JSONParseException;

import java.util.List;

/**
 * created by xuweibin at 2025/3/26
 */
class Resolver {

    private final List<Token> tokenList;
    private int current;
    private JsonValue jsonValue;

    Resolver(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    void resolve() {
        jsonValue = resolveToken();
        if (peek() != Token.EOF) {
            throw new JSONParseException("unexpect eof");
        }
    }

    private JsonValue resolveToken() {
        Token token = advance();
        return switch (token.type) {
            case LEFT_BRACE -> jsonObject();
            case LEFT_SQUARE -> jsonArray();
            case STRING -> new JsonString((String) token.value);
            case NUMBER -> new JsonNumber((String) token.value);
            case FALSE -> Constant.FALSE;
            case TRUE -> Constant.TRUE;
            case NULL -> Constant.NULL;
            default -> null;
        };
    }

    private JsonObject jsonObject() {
        JsonObject jsonObject = new JsonObject();
        while (!isEnd() && peek().type != TokenType.RIGHT_BRACE) {
            Token key = require(TokenType.STRING);
            require(TokenType.COLON);
            JsonValue value = resolveToken();
            if (key.value != null) {
                jsonObject.add(key.value.toString(), value);
            }
            Token token = peek();
            if (token.type != TokenType.RIGHT_BRACE && token.type != TokenType.COMMA) {
                throw new JSONParseException("except token: } ,, but get " + token.type);
            }
        }
        return jsonObject;
    }

    private JsonArray jsonArray() {
        JsonArray jsonArray = new JsonArray();
        int length = 0;
        while (!isEnd() && peek().type != TokenType.RIGHT_SQUARE) {
            JsonValue value = resolveToken();
            jsonArray.add(value);
            length++;
        }
        jsonArray.size = length;
        return jsonArray;
    }

    private Token require(TokenType type) {
        if (isEnd()) throw new JSONParseException("unexpect eof");
        Token token = peek();
        if (token.type == type) {
            return advance();
        }
        throw new JSONParseException("require " + type + ", but get " + token.type);
    }

    private Token advance() {
        current++;
        return tokenList.get(current - 1);
    }

    private Token peek() {
        if (isEnd()) return Token.EOF;
        return tokenList.get(current);
    }

    private boolean isEnd() {
        return current >= tokenList.size() || tokenList.get(current).type == TokenType.EOF;
    }
}
