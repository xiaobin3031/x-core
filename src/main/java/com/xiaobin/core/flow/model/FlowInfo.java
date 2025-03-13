package com.xiaobin.core.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * created by xuweibin at 2024/11/26 11:31
 */
@Setter
@Getter
public class FlowInfo {

    public FlowInfo(int flowId, String flowName, int flowType) {
        this.flowId = flowId;
        this.flowName = flowName;
        this.flowType = flowType;
    }

    private final int flowId;

    private String flowName;

    private final int flowType;

    private Boolean ended;

    private String errorMsg;

    /**
     * 是否自动
     */
    private boolean automatic;

    private Map<String, Object> extMap = new HashMap<>();
}
