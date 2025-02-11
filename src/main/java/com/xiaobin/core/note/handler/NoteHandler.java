package com.xiaobin.core.note.handler;

import com.xiaobin.core.dao.service.SqlServiceProxy;
import com.xiaobin.core.note.DbService;
import com.xiaobin.core.note.model.Note;
import com.xiaobin.core.server.config.Get;
import com.xiaobin.core.server.config.Post;
import com.xiaobin.core.server.config.Request;

import java.util.List;

/**
 * created by xuweibin at 2025/2/11 12:33
 */
@Request("/note")
public class NoteHandler {

    private final SqlServiceProxy<DbService> sqlServiceProxy;
    private final DbService dbService;

    public NoteHandler(SqlServiceProxy<DbService> sqlServiceProxy) {
        this.sqlServiceProxy = sqlServiceProxy;
        this.dbService = sqlServiceProxy.getService();
    }

    @Get("/list")
    public List<Note> list() {
        return dbService.loadByDeleted(Note.class, false);
    }

    @Post("/add")
    public void add(Note note) {
        Note add = new Note();
        add.setName(note.getName());
        sqlServiceProxy.getSqlFactory().save(add);
    }

    @Post("/update")
    public void update(Note note) {
        Note one = this.dbService.loadOneByIdAndDeleted(Note.class, note.getId(), false);
        if (one == null) {
            throw new RuntimeException("record not exist");
        }
        one.setId(note.getId());
        one.setName(note.getName());
        sqlServiceProxy.getSqlFactory().updateById(one, null);
    }
}
