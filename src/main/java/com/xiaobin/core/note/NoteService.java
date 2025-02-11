package com.xiaobin.core.note;

import com.xiaobin.core.dao.service.SqlServiceProxy;
import com.xiaobin.core.note.handler.NoteContentHandler;
import com.xiaobin.core.note.handler.NoteHandler;
import com.xiaobin.core.server.MyHttpServer;

/**
 * created by xuweibin at 2025/2/11 11:04
 */
public class NoteService {

    public static void main(String[] args) {
        SqlServiceProxy<DbService> sqlProxy = new SqlServiceProxy<>("x-core", null, DbService.class);
        Object[] handlers = new Object[]{
                new NoteHandler(sqlProxy),
                new NoteContentHandler(sqlProxy)
        };
        MyHttpServer.createServer(6547, handlers);
    }
}
