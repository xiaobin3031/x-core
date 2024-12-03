package com.xiaobin.core.bean.annotation;

import com.xiaobin.core.constant.XBeanIgnoreScene;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * created by xuweibin at 2024/8/26 17:44
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface XBean {

    /**
     * 描述
     */
    String description();

    /**
     * 可选，默认为false，必输
     */
    boolean optional() default false;

    /**
     * 默认值
     */
    String defaultValue() default "";

    /**
     * 在指定场景下忽略
     */
    XBeanIgnoreScene[] ignoreScene() default XBeanIgnoreScene.NONE;
}
