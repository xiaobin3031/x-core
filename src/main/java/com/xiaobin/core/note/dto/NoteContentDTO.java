package com.xiaobin.core.note.dto;

import com.xiaobin.core.note.model.NoteContent;
import com.xiaobin.core.note.model.NoteTableDatas;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by xuweibin at 2025/2/12 15:04
 */
@Getter
@Setter
public class NoteContentDTO extends NoteContent {

    private List<NoteTableDatas> datas;
}
