package com.xiaobin.core.json.exception;

/**
 * created by xuweibin at 2024/12/10 15:50
 */
public class JSONEofException extends RuntimeException {

    public JSONEofException() {
        super("json content need more characters, but get EOF!");
    }
}
