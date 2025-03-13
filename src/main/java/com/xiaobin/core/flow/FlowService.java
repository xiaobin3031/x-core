package com.xiaobin.core.flow;

import com.xiaobin.core.flow.model.FlowExecRequest;
import com.xiaobin.core.flow.model.FlowExecResult;
import com.xiaobin.core.flow.model.FlowInfo;

/**
 * created by xuweibin at 2024/11/26 11:24
 */
public abstract class FlowService<T extends FlowExecRequest> {

    protected final FlowInfo flowInfo;

    public FlowService(int flowId, String flowName, int flowType) {
        this.flowInfo = new FlowInfo(flowId, flowName, flowType);
    }

    /**
     * 执行流程操作
     */
    public abstract void exec(T request, FlowExecResult result);

    /**
     * 判断当前流程是否结束
     *
     * @return true 已结束，false 未结束
     */
    public boolean isEnd() {
        return Boolean.TRUE.equals(this.flowInfo.getEnded());
    }

    /**
     * 执行过程中的错误信息
     *
     * @return null 无错误， 不为null 错误信息
     */
    public String errorInfo() {
        return this.flowInfo.getErrorMsg();
    }

    public int flowId() {
        return this.flowInfo.getFlowId();
    }

    public FlowInfo flowInfo() {
        return this.flowInfo;
    }

    public Object getExtData() {
        return null;
    }

    public FlowService<T> setFlowName(String flowName) {
        this.flowInfo.setFlowName(flowName);
        return this;
    }
}
