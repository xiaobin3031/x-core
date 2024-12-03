package com.xiaobin.core.dao.service;

import java.util.List;

/**
 * created by xuweibin at 2024/11/22 9:01
 * 写个样例
 */
public interface DaoService {

    <T> List<T> loadByIdAndDeleted(Class<T> cls, Long orderId, int deleted);

    <T> List<T> loadByIdAndDeleted(Class<T> cls, List<Long> orderId, int deleted);

    <T> List<T> loadByOrderIdAndDeleted(Class<T> cls, List<Long> orderId, int deleted);

    <T> List<T> loadByOrderIdAndDeleted(Class<T> cls, Long orderId, int deleted);
}
