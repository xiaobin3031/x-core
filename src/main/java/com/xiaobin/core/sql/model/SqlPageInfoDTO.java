package com.xiaobin.core.sql.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * created by xuweibin at 2025/3/19
 */
@Getter
@Setter
public class SqlPageInfoDTO {

    private List<Map<String, String>> cols;

    private List<Map<String, Object>> datas;

    private long total;
}
