package com.xiaobin.core.sql.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * created by xuweibin at 2025/3/19
 */
@Getter
@Setter
public class SqlQueryDTO {

    private String connName;

    private String tableName;

    private String schemaName;

    private Map<String, Object> query;

    private int page = 1;

    private int size = 10;
}
