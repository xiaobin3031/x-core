package com.xiaobin.core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by xuweibin at 2024/11/25 8:52
 */
public class MemoryData<T> {

    private final Map<String, List<T>> ERROR_DATA_MAP = new HashMap<>();

    public void add(String key, List<T> list) {
        List<T> longs = ERROR_DATA_MAP.get(key);
        if (longs != null) {
            list.removeAll(longs);
            longs.addAll(list);
        } else {
            longs = list;
        }
        ERROR_DATA_MAP.put(key, longs);
    }

    public void add(String key, T id) {
        List<T> longs = ERROR_DATA_MAP.get(key);
        if (longs == null) {
            longs = new ArrayList<>();
            longs.add(id);
            ERROR_DATA_MAP.put(key, longs);
        } else if (!longs.contains(id)) {
            longs.add(id);
        }
    }

    public List<T> get(String key) {
        return ERROR_DATA_MAP.getOrDefault(key, new ArrayList<>());
    }

    public List<T> remove(String key) {
        return ERROR_DATA_MAP.remove(key);
    }

    public boolean contains(String key, T id) {
        List<T> longs = ERROR_DATA_MAP.get(key);
        return longs != null && longs.contains(id);
    }
}
