package com.xiaobin.core.data_check;

import com.xiaobin.core.dao.DbConfig;
import com.xiaobin.core.data_check.handler.DataCheckHandler;
import com.xiaobin.core.data_check.model.DataCheckModel;
import com.xiaobin.core.log.SysLogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by xuweibin at 2025/3/13
 * 数据校验
 */
public class DataCheckService {

    private static final Pattern MATCH_VARIABLE = Pattern.compile("\\$\\{([^}]+)}");

    private final Map<String, Object> storiedData = new HashMap<>();

    /**
     * 检查数据
     */
    public void check(List<DataCheckModel> dataCheckModels) {

        for (DataCheckModel dataCheckModel : dataCheckModels) {
            List<Map<String, Object>> list = loadData(dataCheckModel);
            storiedData.put(dataCheckModel.getStoreVariable(), list);
        }
    }

    /**
     * 加载数据
     *
     * @param dataCheckModel
     * @return
     */
    private List<Map<String, Object>> loadData(DataCheckModel dataCheckModel) {
        List<Map<String, Object>> list = new ArrayList<>();
        String plainSql = dataCheckModel.getSql();
        List<Object> values = new ArrayList<>();
        while (true) {
            int index = plainSql.indexOf("${");
            if (index == -1) break;
            int index2 = plainSql.indexOf("}", index + 2);
            if (index2 == -1) break;
            String expression = plainSql.substring(index + 2, index2);
            Object value = getData(expression);
            values.add(value);
            plainSql = plainSql.substring(0, index) + " ? " + plainSql.substring(index2 + 1);
        }
        Connection conn = DbConfig.getConn(dataCheckModel.getDbConfigName());
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(plainSql);
            for (int i = 0; i < values.size(); i++) {
                Object object = values.get(i);
                ps.setObject(i + 1, object);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                Map<String, Object> items = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(columnName);
                    items.put(columnName, value);
                }
                list.add(items);
            }
        } catch (Exception e) {
            SysLogUtil.logError("load data error: " + e.getMessage());
        } finally {
            DbConfig.close(null, ps, rs);
        }
        return list;
    }

    /**
     * 校验当前的数据
     *
     * @param dataCheckModel
     */
    private void checkData(DataCheckModel dataCheckModel, List<Map<String, Object>> list) {
        List<String> checkCmds = dataCheckModel.getCheckCmds();
        if (checkCmds == null || checkCmds.isEmpty()) return;

        for (Map<String, Object> dataMap : list) {

        }
    }

    /**
     * 获取数据
     *
     * @param expression 表达式
     */
    private Object getData(String expression) {

        return null;
    }
}
