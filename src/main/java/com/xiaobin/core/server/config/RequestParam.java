package com.xiaobin.core.server.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author xuweibin
 * @date 12/29/24
 * @description
 */
@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
public @interface RequestParam {

    String value() default "";
}
