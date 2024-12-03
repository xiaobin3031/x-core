package com.xiaobin.core.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * created by xuweibin at 2024/11/22 20:55
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface Entity {

     String tableName() default "";

     String schema();
}
