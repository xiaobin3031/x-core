package com.xiaobin.core.note.handler;

import com.xiaobin.core.dao.service.SqlServiceProxy;
import com.xiaobin.core.json.JSON;
import com.xiaobin.core.note.DbService;
import com.xiaobin.core.note.constant.Constant;
import com.xiaobin.core.note.dto.NoteContentDTO;
import com.xiaobin.core.note.dto.NoteTableDTO;
import com.xiaobin.core.note.dto.TableAddFieldDTO;
import com.xiaobin.core.note.model.Note;
import com.xiaobin.core.note.model.NoteContent;
import com.xiaobin.core.note.model.NoteTableDatas;
import com.xiaobin.core.server.config.Get;
import com.xiaobin.core.server.config.Post;
import com.xiaobin.core.server.config.Request;
import com.xiaobin.core.server.config.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public NoteContent add(NoteContentDTO noteContent) {
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
        add.setAfterId(noteContent.getAfterId());
        if (noteContent.getHeads() != null) {
            add.setHeads(noteContent.getHeads());
        }

        if (noteContent.getOrders() != null) {
            add.setOrders(noteContent.getOrders());
        }
        if (noteContent.getTypes() != null) {
            add.setOrders(noteContent.getTypes());
        }

        sqlServiceProxy.getSqlFactory().execTransaction(() -> {
            sqlServiceProxy.getSqlFactory().save(add);
            if (Constant.NOTE_TYPE_TABLE == noteContent.getType() && noteContent.getDatas() != null && !noteContent.getDatas().isEmpty()) {
                List<NoteTableDatas> datas = new ArrayList<>();
                for (NoteTableDatas data : noteContent.getDatas()) {
                    NoteTableDatas noteTableDatas = new NoteTableDatas();
                    noteTableDatas.setContentId(add.getId());
                    noteTableDatas.setDatas(data.getDatas());
                    noteTableDatas.setAfterId(data.getAfterId());
                    sqlServiceProxy.getSqlFactory().save(noteTableDatas);
                    datas.add(noteTableDatas);
                }
                noteContent.getDatas().clear();
                noteContent.getDatas().addAll(datas);
            }
            return true;
        });
        noteContent.setId(add.getId());
        return noteContent;
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
        update.setDeleted(noteContent.getDeleted());
        if (noteContent.getHeads() != null) {
            update.setHeads(noteContent.getHeads());
        }

        if (noteContent.getOrders() != null) {
            update.setOrders(noteContent.getOrders());
        }
        if (noteContent.getTypes() != null) {
            update.setOrders(noteContent.getTypes());
        }
        sqlServiceProxy.getSqlFactory().updateById(update);
    }

    @Get("/table-data-list")
    public List<NoteTableDatas> listTableData(@RequestParam("contentIds") List<Integer> contentIds) {
        return dbService.loadByContentIdAndDeleted(NoteTableDatas.class, contentIds, false);
    }

    @Post("/table-data-delete")
    public void tableDataDelete(Integer id) {
        NoteTableDatas noteTableDatas = dbService.loadOneByIdAndDeleted(NoteTableDatas.class, id, false);
        if (noteTableDatas == null) {
            throw new RuntimeException("note not exist");
        }
        NoteTableDatas delete = new NoteTableDatas();
        delete.setId(id);
        delete.setDeleted(true);
        sqlServiceProxy.getSqlFactory().updateById(delete);
    }

    @Post("/table-data-update")
    public void tableDataUpdate(NoteTableDatas data) {
        NoteTableDatas noteTableDatas = dbService.loadOneByIdAndDeleted(NoteTableDatas.class, data.getId(), false);
        if (noteTableDatas == null) {
            throw new RuntimeException("note not exist");
        }
        NoteTableDatas update = new NoteTableDatas();
        update.setId(data.getId());
        update.setDatas(data.getDatas());
        update.setAfterId(data.getAfterId());
        sqlServiceProxy.getSqlFactory().updateById(update);
    }

    @Post("/table-add-field")
    public NoteTableDTO tableAddField(TableAddFieldDTO dto) {
        NoteContent noteContent = dbService.loadOneByIdAndDeleted(NoteContent.class, dto.getId(), false);
        Objects.requireNonNull(noteContent, "note content not exist");

        JSON json = new JSON();
        List<String> heads = json.withSource(noteContent.getHeads()).readList(String.class);
        List<Integer> orders = json.withSource(noteContent.getOrders()).readList(Integer.class);
        List<NoteTableDatas> datas = dbService.loadByContentIdAndDeleted(NoteTableDatas.class, noteContent.getId(), false);
        List<NoteTableDatas> updateDatas = new ArrayList<>(datas.size());
        if (dto.isBefore()) {
            // before index
            heads.add(dto.getIndex(), "");
            int order = orders.get(dto.getIndex());
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i) >= order) {
                    orders.set(i, orders.get(i) + 1);
                }
            }
            orders.add(dto.getIndex(), order);

            for (NoteTableDatas data : datas) {
                NoteTableDatas update = new NoteTableDatas();
                update.setId(data.getId());
                List<String> ds = json.withSource(data.getDatas()).readList(String.class);
                ds.add(dto.getIndex(), "");
                update.setDatas(json.parse(ds));
                updateDatas.add(update);
            }
        } else {
            if (dto.getIndex() >= heads.size() - 1) {
                heads.add("");
                Integer max = Collections.max(orders);
                orders.add(max + 1);

                for (NoteTableDatas data : datas) {
                    NoteTableDatas update = new NoteTableDatas();
                    update.setId(data.getId());
                    List<String> ds = json.withSource(data.getDatas()).readList(String.class);
                    ds.add("");
                    update.setDatas(json.parse(ds));
                    updateDatas.add(update);
                }
            } else {
                heads.add(dto.getIndex() + 1, "");
                int order = orders.get(dto.getIndex());
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i) > order) {
                        orders.set(i, orders.get(i) + 1);
                    }
                }
                orders.add(dto.getIndex() + 1, order + 1);

                for (NoteTableDatas data : datas) {
                    NoteTableDatas update = new NoteTableDatas();
                    update.setId(data.getId());
                    List<String> ds = json.withSource(data.getDatas()).readList(String.class);
                    ds.add(dto.getIndex() + 1, "");
                    update.setDatas(json.parse(ds));
                    updateDatas.add(update);
                }
            }
        }
        NoteContent update = new NoteContent();
        update.setId(noteContent.getId());
        update.setHeads(json.parse(heads));
        update.setOrders(json.parse(orders));

        sqlServiceProxy.getSqlFactory().execTransaction(() -> {
            sqlServiceProxy.getSqlFactory().updateById(update);
            updateDatas.forEach(sqlServiceProxy.getSqlFactory()::updateById);
            return true;
        });

        NoteTableDTO noteTableDTO = new NoteTableDTO();
        noteTableDTO.setNoteContent(update);
        noteTableDTO.setDatas(updateDatas);
        return noteTableDTO;
    }

}
