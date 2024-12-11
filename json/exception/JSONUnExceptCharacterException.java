package com.xiaobin.core.json.exception;

/**
 * Created by XWB on 2024-08-04.
 */
public class JSONUnExceptCharacterException extends RuntimeException {

    private static final String FORMAT_STRING = "except character %s at %s, but actual get %s";

    public JSONUnExceptCharacterException(int except, int actual) {
        super("except character " + (char) except + ", actual " + (char) actual);
    }

    public JSONUnExceptCharacterException(String except, int actual) {
        super("except character " + except + ", actual " + (char) actual);
    }

    public JSONUnExceptCharacterException(int except, int actual, int index) {
        super(String.format(FORMAT_STRING, except, index, actual));
    }

    public JSONUnExceptCharacterException(String except, int actual, int index) {
        super(String.format(FORMAT_STRING, except, index, actual));
    }
}
