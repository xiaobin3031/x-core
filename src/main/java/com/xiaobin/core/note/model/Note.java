package com.xiaobin.core.note.model;

import com.xiaobin.core.dao.annotation.Entity;
import com.xiaobin.core.dao.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * created by xuweibin at 2025/2/11 11:04
 */
@Getter
@Setter
@Entity(tableName = "notes", schema = "x-core")
public class Note {

    @Id(type = Id.IdType.AUTO)
    private Integer id;

    private String name;

    private Boolean deleted;

    private LocalDateTime addTime;
}
