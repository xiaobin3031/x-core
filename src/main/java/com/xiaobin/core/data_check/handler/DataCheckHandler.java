package com.xiaobin.core.data_check.handler;

import com.xiaobin.core.dao.SqlFactory;

/**
 * created by xuweibin at 2025/3/13
 */
public class DataCheckHandler {

    private final SqlFactory sqlFactory;

    public DataCheckHandler(SqlFactory sqlFactory) {
        this.sqlFactory = sqlFactory;
    }
}
