package com.xiaobin.core.flow.model;

import lombok.Getter;
import lombok.Setter;

/**
 * created by xuweibin at 2025/3/13
 */
@Getter
@Setter
public class FlowExecRequest {

    private String testId;

    private int sceneId;

    private int flowId;
}
