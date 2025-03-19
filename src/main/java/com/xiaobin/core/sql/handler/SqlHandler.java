package com.xiaobin.core.sql.handler;

import com.xiaobin.core.dao.SqlFactory;
import com.xiaobin.core.server.config.Post;
import com.xiaobin.core.server.config.Request;
import com.xiaobin.core.sql.model.SqlPageInfoDTO;
import com.xiaobin.core.sql.model.SqlQueryDTO;

/**
 * created by xuweibin at 2025/3/19
 */
@Request("/sql")
public class SqlHandler {

    @Post("/query")
    public SqlPageInfoDTO query(SqlQueryDTO sqlQueryDTO) {
        SqlFactory sqlFactory = new SqlFactory(sqlQueryDTO.getConnName());
//        sqlFactory.load();

        SqlPageInfoDTO sqlPageInfoDTO = new SqlPageInfoDTO();
        return sqlPageInfoDTO;
    }
}
