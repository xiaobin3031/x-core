package com.xiaobin.core.dao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * created by xuweibin at 2025/3/19
 */
@Getter
@Setter
public class PageInfo {

    private int page;

    private int size;

    private long total;

    private List<Map<String, Object>> datas;

    private List<String> cols;
}
