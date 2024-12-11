package com.xiaobin.core.data;

import java.util.*;

/**
 * created by xuweibin at 2024/11/25 8:52
 */
public class MemoryData<T> extends BaseData<T> {

    private List<T> datas = new ArrayList<>();

    public MemoryData() {
        super(false);
    }

    /**
     * 要放文件里，得有Class，不然类型不匹配，反编译出来的类型不一致
     */
    public MemoryData(String filename, Class<T> cls) {
        super(true, cls, filename);

        this.datas = super.loadFromFile();
    }

    public void add(List<T> list) {
        list.stream().filter(a -> !this.datas.contains(a)).forEach(this.datas::add);
        super.storeToFile(this.datas);
    }

    public void add(T id) {
        if (!this.datas.contains(id)) {
            this.datas.add(id);
            super.storeToFile(this.datas);
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
