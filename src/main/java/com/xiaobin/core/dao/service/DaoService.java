package com.xiaobin.core.dao.service;

import java.util.List;

/**
 * created by xuweibin at 2024/11/22 9:01
 * 写个样例
 */
public interface DaoService {

    <T> List<T> loadByIdAndDeleted(Class<T> cls, Object orderId, Integer deleted);

    <T> T loadOneByIdAndDeleted(Class<T> cls, Object orderId, Integer deleted);

    <T> List<T> loadByGoodsIdAndDeleted(Class<T> cls, Object goodsId, Integer deleted);

    <T> List<T> loadByCombGoodsIdAndDeleted(Class<T> cls, Object goodsId, Integer deleted);

    <T> List<T> loadByValueIdAndDeleted(Class<T> cls, Object valueId, Integer deleted);

    <T> List<T> loadByPIdAndDeleted(Class<T> cls, Object orderId, Integer deleted);

    <T> List<T> loadByIdAndDeleted(Class<T> cls, List<Long> orderId, Integer deleted);

    <T> List<T> loadByOrderIdAndDeleted(Class<T> cls, List<Long> orderId, Integer deleted);

    <T> List<T> loadByOrderIdAndDeleted(Class<T> cls, Object orderId, Integer deleted);

    <T> List<T> loadByPayOrderSnAndDeleted(Class<T> cls, String payOrderSn, Integer deleted);

    <T> List<T> loadByTypeAndDeleted(Class<T> cls, List<String> types, Integer deleted);

    <T> List<T> loadByTypeAndDeleted(Class<T> cls, String type, Integer deleted);
}
