package com.xiaobin.core;

import com.xiaobin.core.dao.SqlFactory;
import com.xiaobin.core.server.MyHttpServer;

/**
 * created by xuweibin at 2025/3/13
 */
public class XCoreService {

    public static void main(String[] args) {
        SqlFactory sqlFactory = new SqlFactory("x-core");
        Object[] handlers = new Object[]{
//                new DataCheckHandler(sqlFactory)
        };
        MyHttpServer.createServer(6547, handlers);
    }
}
