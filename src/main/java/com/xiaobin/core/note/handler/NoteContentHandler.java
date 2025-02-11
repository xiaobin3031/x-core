package com.xiaobin.core.note.handler;

import com.xiaobin.core.dao.service.SqlServiceProxy;
import com.xiaobin.core.note.DbService;
import com.xiaobin.core.note.model.Note;
import com.xiaobin.core.note.model.NoteContent;
import com.xiaobin.core.server.config.Get;
import com.xiaobin.core.server.config.Post;
import com.xiaobin.core.server.config.Request;
import com.xiaobin.core.server.config.RequestParam;

import java.util.List;

/**
 * created by xuweibin at 2025/2/11 15:18
 */
@Request("/note-content")
public class NoteContentHandler {

    private final SqlServiceProxy<DbService> sqlServiceProxy;
    private final DbService dbService;

    public NoteContentHandler(SqlServiceProxy<DbService> sqlServiceProxy) {
        this.sqlServiceProxy = sqlServiceProxy;
        dbService = sqlServiceProxy.getService();
    }

    @Get("/list")
    public List<NoteContent> listByNoteId(@RequestParam("noteId") Long noteId) {
        return dbService.loadByNoteIdAndDeleted(NoteContent.class, noteId, false);
    }

    @Post("/add")
    public NoteContent add(NoteContent noteContent) {
        Note note = dbService.loadOneByIdAndDeleted(Note.class, noteContent.getNoteId(), false);
        if (note == null) {
            throw new RuntimeException("note not exist");
        }
        NoteContent add = new NoteContent();
        add.setContent(noteContent.getContent());
        add.setChecked(noteContent.getChecked());
        add.setNoteContentId(noteContent.getNoteContentId());
        add.setNoteId(note.getId());
        add.setType(noteContent.getType());
        sqlServiceProxy.getSqlFactory().save(add);
        return add;
    }

    @Post("/update")
    public void update(NoteContent noteContent) {
        Note note = dbService.loadOneByIdAndDeleted(Note.class, noteContent.getNoteId(), false);
        if (note == null) {
            throw new RuntimeException("note not exist");
        }
        NoteContent update = new NoteContent();
        update.setId(noteContent.getId());
        update.setContent(noteContent.getContent());
        update.setChecked(noteContent.getChecked());
        update.setNoteContentId(noteContent.getNoteContentId());
        update.setNoteId(note.getId());
        update.setType(noteContent.getType());
        update.setAfterId(noteContent.getAfterId());
        sqlServiceProxy.getSqlFactory().updateById(update);
    }
}
