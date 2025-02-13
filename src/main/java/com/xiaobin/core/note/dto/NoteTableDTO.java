package com.xiaobin.core.note.dto;

import com.xiaobin.core.note.model.NoteContent;
import com.xiaobin.core.note.model.NoteTableDatas;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by xuweibin at 2025/2/12 13:36
 */
@Getter
@Setter
public class NoteTableDTO {

    private NoteContent noteContent;

    private List<NoteTableDatas> datas;
}
