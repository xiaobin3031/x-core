package com.xiaobin.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 * created by xuweibin at 2024/12/17 13:07
 * 带版本控制的数据
 */
public class VersionData {

    private static final Map<String, Map<String, VersionData>> VERSION_DATA_MAP = new HashMap<>();

    private final Map<String, Object> dataMap = new HashMap<>();

    public static VersionData init(String versionKey, String version) {
        Map<String, VersionData> versionDataMap = VERSION_DATA_MAP.computeIfAbsent(versionKey, k -> new HashMap<>());
        VersionData versionData = versionDataMap.get(version);
        if (versionData == null) {
            versionDataMap.clear();
            versionData = new VersionData();
            versionDataMap.put(version, versionData);
        }
        return versionData;
    }

    private VersionData() {
    }

    public void setData(String key, Object data) {
        dataMap.put(key, data);
    }

    public Object getData(String key) {
        return dataMap.get(key);
    }
}
