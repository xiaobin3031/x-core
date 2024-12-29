package com.xiaobin.core.bean.model;

import com.xiaobin.core.constant.XBeanIgnoreScene;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * created by xuweibin at 2024/8/26 18:01
 */
@Getter
@Setter
public class BeanModel {

    private Field field;

    private String fieldName;

    /**
     * 输入时是否可选
     * 默认 true
     */
    private boolean optional = true;

    private String description;

    /**
     * 默认值
     */
    private String defaultValue;

    private Method getMethod;

    private Method setMethod;

    private XBeanIgnoreScene[] ignoreScene;

    public boolean ignoreSceneContainers(XBeanIgnoreScene ignoreScene) {
        if (this.ignoreScene != null) {
            for (XBeanIgnoreScene is : this.ignoreScene) {
                if (is == ignoreScene || is == XBeanIgnoreScene.ALL) {
                    return true;
                }
            }
        }

        return false;
    }
}
