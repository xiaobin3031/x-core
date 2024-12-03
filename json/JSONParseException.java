package com.xiaobin.core.json;

/**
 * Created by XWB on 2024-08-03.
 */
public class JSONParseException extends RuntimeException{

    public JSONParseException(String msg){
        super(msg);
    }

    public JSONParseException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
