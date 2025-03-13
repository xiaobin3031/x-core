package com.xiaobin.core.flow;

import com.xiaobin.core.flow.model.FlowExecRequest;
import com.xiaobin.core.flow.model.FlowExecResult;
import com.xiaobin.core.flow.model.FlowMgr;
import com.xiaobin.core.flow.model.Scenes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by xuweibin at 2024/11/26 13:38
 */
public class FlowManagerService {

    private static final Map<String, FlowMgr> FLOW_MGR_MAP = new HashMap<>();

    public void register(FlowMgr flowMgr) {
        FLOW_MGR_MAP.put(flowMgr.getTestId(), flowMgr);
    }

    /**
     * 新建测试流程
     */

    public FlowMgr load(String testId) {
        return FLOW_MGR_MAP.get(testId);
    }

    public void clearTest(String testId) {
        FLOW_MGR_MAP.remove(testId);
    }

    public FlowExecResult exec(FlowExecRequest request) {
        FlowMgr flowMgr = this.load(request.getTestId());
        if (flowMgr == null) {
            throw new RuntimeException("流程不存在，请重新创建");
        }

        int sceneId;
        if (flowMgr.getCurSceneId() != request.getSceneId()) {
            // 不是当前场景，看看当前是否全部结束，如果是，就选择指定场景
            Scenes scenes = flowMgr.getSceneList().stream().filter(a -> a.getSceneId() == flowMgr.getCurSceneId())
                    .findFirst()
                    .orElse(null);
            if (scenes == null) throw new RuntimeException("场景不存在");
            if (scenes.getFlowServiceList().stream().allMatch(FlowService::isEnd) || scenes.getFlowServiceList().stream().noneMatch(FlowService::isEnd)) {
                // 所有都完成，或者所有都未完成，允许切换到指定场景
                sceneId = request.getSceneId();
                flowMgr.setCurSceneId(sceneId);
            } else {
                // 当前场景没完成
                throw new RuntimeException("场景不匹配，当前场景是: " + scenes.getSceneName());
            }
        } else {
            sceneId = flowMgr.getCurSceneId();
        }

        List<Scenes> sceneList = flowMgr.getSceneList();
        int sceneIndex = 0;
        Scenes scenes = null;
        for (int i = 0; i < sceneList.size(); i++) {
            if (sceneList.get(i).getSceneId() == sceneId) {
                sceneIndex = i;
                scenes = sceneList.get(i);
                break;
            }
        }
        if (scenes == null) throw new RuntimeException("场景不存在");

        List<FlowService<FlowExecRequest>> flowServiceList = scenes.getFlowServiceList();
        int flowIndex = 0;
        FlowService<FlowExecRequest> flowService = null;
        for (int i = 0; i < flowServiceList.size(); i++) {
            if (flowServiceList.get(i).flowId() == scenes.getCurFlowId()) {
                flowIndex = i;
                flowService = flowServiceList.get(i);
                break;
            }
        }
        if (flowService == null) throw new RuntimeException("流程不存在");

        if (scenes.getCurFlowId() != request.getFlowId()) {
            throw new RuntimeException("流程不匹配，当前流程是: " + flowService.flowInfo().getFlowName());
        }

        int curSceneId = scenes.getSceneId();
        int curFlowId = flowService.flowId();

        FlowExecResult result = new FlowExecResult();
        flowService.flowInfo().setErrorMsg(null);
        flowService.exec(request, result);
        result.setEnd(flowService.isEnd());
        if (flowService.isEnd()) {
            if (flowIndex < flowServiceList.size() - 1) {
                curFlowId = flowServiceList.get(flowIndex + 1).flowId();
                scenes.setCurFlowId(curFlowId);
            } else {
                // 已经是最后一个流程了，并且已经结束
                result.setFinish(true);
                if (sceneIndex >= sceneList.size() - 1) {
                    // 所有流程都结束
                    flowMgr.setFinish(true);
                    curSceneId = -1;
                    curFlowId = -1;
                } else {
                    Scenes nextScene = sceneList.get(sceneIndex + 1);
                    flowMgr.setCurSceneId(nextScene.getSceneId());
                    nextScene.setCurFlowId(nextScene.getFlowServiceList().get(0).flowId());

                    curSceneId = nextScene.getSceneId();
                    curFlowId = nextScene.getCurFlowId();
                }
            }
        }
        result.setErrMsg(flowService.errorInfo());
        result.setCurSceneId(curSceneId);
        result.setCurFlowId(curFlowId);

        return result;
    }
}
