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

    void scan() {
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

    private void scanToken() {
        while (!isAtEnd()) {
            start = current;
            scan();
        }
        addToken(TokenType.EOF, null);
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

    private void addToken(Token token) {
        tokenList.add(token);
    }

    public static void main(String[] args) {
        String string = "{\"orderType\":1,\"invoiceTime\":\"2025-03-26 10:11:49\",\"shipTime\":\"2025-03-26 10:18:16\",\"plazaCode\":\"0001\",\"returnFlag\":false,\"openboxFlag\":0,\"recordChannel\":\"WXX\",\"id\":20022418,\"consignee\":\"uAsEcudn2/cuTMMPo5/Jmw==\",\"shipCouponPrice\":0.00,\"orderJf\":0.00,\"cartId\":20004531,\"payMid\":\"1602459945\",\"outsystemFs\":false,\"shipExpireTime\":\"2025-03-26 13:13:03\",\"plazaFreightRate\":0.00,\"platformAmount\":0.00,\"salesUserId\":6053714,\"exportFlag\":false,\"brandId\":100218,\"goodsPrice\":0.06,\"salesShareDirect\":false,\"platformRefundAmount\":0.00,\"visibleFlag\":true,\"payOrderSn\":\"1000120250326285830079\",\"actualPrice\":0.06,\"promotionPrice\":0.00,\"orderSn\":\"1000120250326285830079\",\"latitude\":29.80908284505208,\"couponMallPrice\":0.00,\"shipChannel\":\"韵达快递\",\"recordNum\":1,\"salesAmount\":0.00,\"integralPrice\":0.00,\"shipId\":86,\"address\":\"pZVCNAqszGS4dGOXibOVSQFhNLmphWJ6IRjC6aDPRy7ERghgzzp4Pw6Xx2rB/RmsWx+8xvtsMRYL6ZT4GfiGGoX0U/HglL4SRWLO24r2yNZ+jMzos9Jhk0LulhT6iRsLqbOGV8CeLxPoL19mVDzKIg==\",\"updateTime\":\"2025-03-26 17:21:50\",\"userId\":7500474,\"payTotal\":0.06,\"recordTime\":\"2025-03-26 17:18:33\",\"deleted\":false,\"overTimeType\":1,\"endTime\":\"2025-03-26 17:21:51\",\"shipShopId\":0,\"shopFreightRate\":100.00,\"addTime\":\"2025-03-26 10:12:55\",\"payTime\":\"2025-03-26 10:13:02\",\"salesGoodsPrice\":0.00,\"salesPlazaCode\":\"0001\",\"salesFlag\":true,\"sourcePlazaCode\":\"0000\",\"recordFlag\":true,\"visibleChannel\":1,\"shopId\":1000043,\"cashPrice\":0.00,\"presellFlag\":false,\"freightPrice\":0.00,\"orderMark\":0,\"longitude\":121.54808648003473,\"shipSn\":\"YD455555543\",\"payExpireTime\":\"2025-03-26 10:27:56\",\"shipSource\":2,\"overShipCouponId\":18749,\"pId\":0,\"goodsType\":1,\"shipType\":2,\"payId\":\"4200002673202503262066491125\",\"payCode\":\"01\",\"payRequest\":\"\",\"acctSplitted\":1,\"partShipFlag\":true,\"shipPlazaCode\":\"\",\"userCid\":\"9900005875829\",\"orderStatus\":403,\"shipName\":\"测试接口\",\"presellFinishType\":0,\"aftersaleFlag\":2,\"sourceShopId\":1004218,\"vipPrice\":0.00,\"depositPrice\":0.00,\"orderPrice\":0.06,\"supportAftersaleType\":\"[2,1,3]\",\"orderSource\":\"WXX\",\"comments\":0,\"mobile\":\"lx8uczjzyOPKOF+nQWpUWw==\",\"payChannel\":\"WXPAY\",\"couponPrice\":0.00,\"refundApplyId\":0,\"aftersaleRealType\":0,\"aftersaleShipSource\":0}";
        Scanner scanner = new Scanner(string);
        scanner.scanToken();
        System.out.println("====");
    }
}
