package com.xiaobin.core.json.exception;

/**
 * Created by XWB on 2024-08-04.
 */
public class JSONUnExceptCharacterException extends RuntimeException {

    public JSONUnExceptCharacterException(int except, int actual) {
        super("except character " + (char) except + ", actual " + (char) actual);
    }

    public JSONUnExceptCharacterException(String except, int actual) {
        super("except character " + except + ", actual " + (char) actual);
    }
}
