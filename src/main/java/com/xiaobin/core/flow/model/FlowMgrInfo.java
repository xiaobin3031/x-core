package com.xiaobin.core.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by xuweibin at 2024/11/28 13:48
 */
@Getter
@Setter
public class FlowMgrInfo {

    private String testId;

    private int curSceneId;

    private List<SceneInfo> sceneInfoList;

    @Getter
    @Setter
    public static class SceneInfo {

        private int sceneId;

        private List<FlowAndDataInfo> flowInfoList;

        private int curFlowId;

        private String sceneName;
    }

    @Getter
    @Setter
    public static class FlowAndDataInfo{

        private FlowInfo flowInfo;

        private Object extData;
    }
}
