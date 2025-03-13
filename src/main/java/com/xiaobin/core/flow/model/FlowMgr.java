package com.xiaobin.core.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by xuweibin at 2024/11/26 13:39
 */
@Getter
@Setter
public class FlowMgr {

    private String testId;

    private int curSceneId;

    private List<Scenes> sceneList;

    private boolean finish;
}
