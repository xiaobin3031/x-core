package com.xiaobin.core.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * created by xuweibin at 2024/12/6 13:10
 */
@Getter
@Setter
public class FlowExecResult {

    private int curSceneId;

    private int curFlowId;

    private boolean finish;

    private boolean end;

    private String errMsg;

    private Map<String, Object> extData = new HashMap<>();
}
