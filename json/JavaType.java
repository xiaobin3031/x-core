package com.xiaobin.core.json;

import lombok.Getter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * created by xuweibin at 2024/8/6 11:31
 */
@Getter
public abstract class JavaType<T> {

    private final Class<?> type;
    private final Type[] actualArgs;

    public JavaType(){
        Type actualArg = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if(actualArg instanceof ParameterizedType aa){
            this.type = (Class<?>) aa.getRawType();
            this.actualArgs = aa.getActualTypeArguments();
        }else if(actualArg instanceof Class<?>){
            this.type = (Class<?>) actualArg;
            this.actualArgs = new Class<?>[0];
        }else{
            this.type = actualArg.getClass();
            this.actualArgs = new Class<?>[0];
        }
    }
}
