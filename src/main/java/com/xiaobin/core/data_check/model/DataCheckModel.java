package com.xiaobin.core.data_check.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by xuweibin at 2025/3/13
 */
@Getter
@Setter
public class DataCheckModel {

    /**
     * 数据库配置名称
     */
    private String dbConfigName;

    /**
     * sql语句
     */
    private String sql;

    /**
     * sql查询结果保存的变量名
     */
    private String storeVariable;

    /**
     * 当前步骤检查的命令
     */
    private List<String> checkCmds;
}
