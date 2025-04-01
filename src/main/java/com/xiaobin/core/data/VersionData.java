package com.xiaobin.core.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * created by xuweibin at 2024/12/17 13:07
 * 带版本控制的数据
 */
public class VersionData {

    private final String versionKey;
    private final String version;

    private static final Map<String, Map<String, VersionData>> VERSION_DATA_MAP = new HashMap<>();

    private final Map<String, Object> dataMap = new HashMap<>();
    private final Set<String> nullKeySet = new HashSet<>();

    public static VersionData init(String versionKey, String version) {
        Map<String, VersionData> versionDataMap = VERSION_DATA_MAP.computeIfAbsent(versionKey, k -> new HashMap<>());
        VersionData versionData = versionDataMap.get(version);
        if (versionData == null) {
            versionDataMap.clear();
            versionData = new VersionData(versionKey, version);
            versionDataMap.put(version, versionData);
        }
        return versionData;
    }

    private VersionData(String versionKey, String version) {
        this.versionKey = versionKey;
        this.version = version;
    }

    public void setData(String key, Object data) {
        if (data == null) {
            nullKeySet.add(key);
        } else {
            dataMap.put(key, data);
        }
    }

    public Object getData(String key) {
        return dataMap.get(key);
    }

    public boolean isNullKey(String key) {
        return nullKeySet.contains(key);
    }

    public void destroy() {
        Map<String, VersionData> map = VERSION_DATA_MAP.get(this.versionKey);
        if (map != null) {
            map.remove(this.version);
            if (map.isEmpty()) {
                VERSION_DATA_MAP.remove(this.versionKey);
            }
        }
    }
}
