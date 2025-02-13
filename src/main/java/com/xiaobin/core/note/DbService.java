package com.xiaobin.core.note;

import java.util.Collection;
import java.util.List;

/**
 * created by xuweibin at 2025/2/11 12:35
 */
public interface DbService {

    <T> List<T> loadByDeleted(Class<T> cls, Boolean deleted);

    <T> T loadOneByIdAndDeleted(Class<T> cls, Object id, Boolean deleted);

    <T> List<T> loadByNoteIdAndDeleted(Class<T> cls, Object noteId, Boolean deleted);

    <T> List<T> loadByContentIdAndDeleted(Class<T> cls, Collection<?> noteId, Boolean deleted);

    <T> List<T> loadByContentIdAndDeleted(Class<T> cls, Object noteId, Boolean deleted);
}
