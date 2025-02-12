package com.xiaobin.core.note.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * created by xuweibin at 2025/2/12 13:36
 */
@Getter
@Setter
public class NoteTableDTO {

    private Integer id;

    private List<String> headers;

    private List<Integer> orders;

    private List<Integer> types;
}
