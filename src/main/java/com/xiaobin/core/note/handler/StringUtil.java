package com.xiaobin.core.note.handler;

import java.util.List;

/**
 * created by xuweibin at 2025/2/12 13:42
 */
public class StringUtil {

    public static String listToArrayString(List<?> list) {
        if (list == null || list.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (Object o : list) {
            sb.append(o).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "]");
        return sb.toString();
    }
}
