package com.xiaobin.core.note.model;

import com.xiaobin.core.dao.annotation.Entity;
import com.xiaobin.core.dao.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * created by xuweibin at 2025/2/11 15:10
 */
@Getter
@Setter
@Entity(schema = "x-core")
public class NoteContent {

    @Id(type = Id.IdType.AUTO)
    private Integer id;

    private Integer noteId;

    private Integer type;

    private String content;

    private Boolean checked;

    private Integer noteContentId;

    private Integer tableId;

    private Integer afterId;

    private LocalDateTime addTime;

    private Boolean deleted;
}
