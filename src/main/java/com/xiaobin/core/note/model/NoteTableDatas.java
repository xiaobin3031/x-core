package com.xiaobin.core.note.model;

import com.xiaobin.core.dao.annotation.Entity;
import com.xiaobin.core.dao.annotation.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * created by xuweibin at 2025/2/12 13:34
 */
@Setter
@Getter
@Entity(schema = "x-core")
public class NoteTableDatas {

    @Id(type = Id.IdType.AUTO)
    private Integer id;

    private Integer contentId;

    private Integer afterId;

    private Integer beforeId;

    private String datas;

    private Boolean deleted;
}
