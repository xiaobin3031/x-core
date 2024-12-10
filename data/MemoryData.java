package com.xiaobin.core.data;

import java.util.*;

/**
 * created by xuweibin at 2024/11/25 8:52
 */
public class MemoryData<T> extends BaseData<T> {

    private final Map<String, List<T>> ERROR_DATA_MAP = new HashMap<>();
    private List<T> datas = new ArrayList<>();

    public MemoryData() {
        super(false);
    }

    public MemoryData(String filename) {
        super(true, filename);

        this.datas = super.loadFromFile();
    }

    public void add(List<T> list) {
        list.stream().filter(a -> !this.datas.contains(a)).forEach(this.datas::add);
        super.storeToFile(this.datas);
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

    public void add(T id) {
        if (!this.datas.contains(id)) {
            this.datas.add(id);
        }
    }

    public List<T> removeAndGetAll() {
        List<T> list = new ArrayList<>(this.datas);
        this.datas.clear();
        super.storeToFile(this.datas);
        return list;
    }

    public boolean contains(T id) {
        return this.datas.contains(id);
    }
}
