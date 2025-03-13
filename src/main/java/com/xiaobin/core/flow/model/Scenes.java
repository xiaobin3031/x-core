package com.xiaobin.core.flow.model;

import com.xiaobin.core.flow.FlowService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * created by xuweibin at 2024/11/26 13:46
 */
@Getter
@Setter
public class Scenes {

    private int sceneId;

    private final List<FlowService<FlowExecRequest>> flowServiceList = new ArrayList<>();

    private int curFlowId;

    /**
     * 当前场景是否结束
     */
    private boolean ended;

    private String sceneName;

    @SuppressWarnings("unchecked")
    public void addFlowService(FlowService<?> flowService) {
        if (curFlowId == 0) {
            curFlowId = flowService.flowId();
        }
        flowServiceList.add((FlowService<FlowExecRequest>) flowService);
    }
}
