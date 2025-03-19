package com.xiaobin.core.dao;

import com.xiaobin.core.dao.annotation.Entity;
import com.xiaobin.core.dao.annotation.Id;
import com.xiaobin.core.dao.model.PageInfo;
import com.xiaobin.core.log.SysLogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * created by xuweibin at 2024/11/20 10:21
 */
public class SqlFactory {

    private final String dbName;

    public SqlFactory(String dbName) {
        this.dbName = dbName;
    }

    public <T> List<T> load(Class<T> cls, SqlPara sqlPara) {
        Entity entity = cls.getDeclaredAnnotation(Entity.class);
        List<T> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        long start = System.currentTimeMillis();
        Connection connection = DbConfig.getConn(this.dbName);
        String tableName = this.getTableName(cls, entity, sqlPara);
        SysLogUtil.logSuccess("load " + tableName + ": ");
        String schema = sqlPara.getSchema();
        if (schema == null || schema.trim().isEmpty()) {
            schema = entity.schema();
        }
        String sql = null;
        try {
            sql = sqlPara.toSql(tableName, schema);
            ps = connection.prepareStatement(sql);
            this.setParam(ps, sqlPara.getValues(), false);
            rs = ps.executeQuery();
            while (rs.next()) {
                T tt = getBeanFromResultSet(cls, rs);
                list.add(tt);
            }
        } catch (SQLException e) {
            System.err.println("load data error: " + e.getMessage());
        } finally {
            DbConfig.close(null, ps, rs);
        }
        long end = System.currentTimeMillis();
        SysLogUtil.logSuccessLn("cast " + (end - start) + " ms");
        return list;
    }

    private String getTableName(Class<?> cls, Entity entity, SqlPara sqlPara) {
        String tableName = null;
        if (sqlPara != null) {
            tableName = sqlPara.getTableName();
        }
        if (tableName == null || tableName.trim().isEmpty()) {
            tableName = entity.tableName();
        }
        if (tableName == null || tableName.isEmpty()) {
            tableName = cls.getSimpleName().substring(0, 1).toLowerCase() + cls.getSimpleName().substring(1).replaceAll("([A-Z])", "_$1").toLowerCase();
        }
        return tableName;
    }

    public <T> int update(T t, SqlPara sqlPara) {
        String where = sqlPara.getWhere();
        if (where.trim().isEmpty()) {
            throw new RuntimeException("update must has where token");
        }
        Class<?> cls = t.getClass();
        Entity entity = this.checkEntity(cls);
        String tableName = this.getTableName(cls, entity, sqlPara);
        if (tableName.trim().isEmpty()) {
            throw new RuntimeException("table is empty");
        }
        String schema = sqlPara.getSchema();
        if (schema == null || schema.trim().isEmpty()) {
            schema = entity.schema();
        }
        Connection connection = DbConfig.getConn(this.dbName);
        PreparedStatement ps = null;
        try {
            Field[] fields = cls.getDeclaredFields();
            List<Object> valueList = new ArrayList<>();
            List<String> columnList = new ArrayList<>();
            for (Field field : fields) {
                String name = field.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = this.getMethod("get" + name, cls);
                if (method == null) {
                    method = this.getMethod("is" + name, cls);
                    if (method == null) continue;
                }
                try {
                    Object invoke = method.invoke(t);
                    valueList.add(invoke);
                    columnList.add(this.fieldToColumn(name));
                } catch (Exception e) {
                    throw new RuntimeException("get value error, cls: " + cls + ", field: " + field.getName());
                }
            }
            if (columnList.isEmpty()) {
                throw new RuntimeException("no update columns");
            }
            StringBuilder stringBuilder = new StringBuilder("update ");
            if (schema != null && !schema.trim().isEmpty()) {
                stringBuilder.append("`").append(schema).append("`.");
            }
            stringBuilder.append(tableName).append(" set ");
            for (String string : columnList) {
                stringBuilder.append(string).append(" = ?, ");
            }
            stringBuilder.setLength(stringBuilder.length() - 2);
            stringBuilder.append(where);
            valueList.addAll(sqlPara.getValues());
            ps = connection.prepareStatement(stringBuilder.toString());
            this.setParam(ps, valueList, false);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("load data error: " + e.getMessage());
        } finally {
            DbConfig.close(null, ps, null);
        }
        return 0;
    }

    public int updateById(Object object) {
        return updateById(object, null);
    }

    public int updateById(Object object, SqlPara sqlPara) {
        Class<?> cls = object.getClass();
        Entity entity = this.checkEntity(cls);
        String tableName = this.getTableName(cls, entity, sqlPara);
        if (tableName.trim().isEmpty()) {
            throw new RuntimeException("table is empty");
        }
        String schema = null;
        if (sqlPara != null) {
            schema = sqlPara.getSchema();
        }
        if (schema == null || schema.trim().isEmpty()) {
            schema = entity.schema();
        }
        Connection connection = DbConfig.getConn(this.dbName);
        PreparedStatement ps = null;
        try {
            Field[] fields = cls.getDeclaredFields();
            List<Object> valueList = new ArrayList<>();
            List<String> columnList = new ArrayList<>();
            List<Object> idValueList = new ArrayList<>();
            List<String> idColumnList = new ArrayList<>();
            for (Field field : fields) {
                String name = field.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = this.getMethod("get" + name, cls);
                if (method == null) {
                    method = this.getMethod("is" + name, cls);
                    if (method == null) continue;
                }
                try {
                    Object invoke = method.invoke(object);
                    if (invoke == null) continue;
                    Id id = field.getDeclaredAnnotation(Id.class);
                    if (id != null) {
                        idValueList.add(invoke);
                        idColumnList.add(this.fieldToColumn(name));
                    } else {
                        valueList.add(invoke);
                        columnList.add(this.fieldToColumn(name));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("get value error, cls: " + cls + ", field: " + field.getName());
                }
            }
            if (columnList.isEmpty() || idColumnList.isEmpty()) {
                throw new RuntimeException("no update columns");
            }
            StringBuilder stringBuilder = new StringBuilder("update ");
            if (schema != null && !schema.trim().isEmpty()) {
                stringBuilder.append("`").append(schema).append("`.");
            }
            stringBuilder.append(tableName).append(" set ");
            for (String string : columnList) {
                stringBuilder.append(string).append(" = ?, ");
            }
            stringBuilder.setLength(stringBuilder.length() - 2);
            stringBuilder.append(" where ");
            for (String string : idColumnList) {
                stringBuilder.append(string).append(" = ?, ");
            }
            stringBuilder.setLength(stringBuilder.length() - 2);
            valueList.addAll(idValueList);
            ps = connection.prepareStatement(stringBuilder.toString());
            this.setParam(ps, valueList, false);
            int i = ps.executeUpdate();
            if (i > 1) {
                connection.rollback();
            }
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("load data error: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("rollback error");
            }
        } finally {
            DbConfig.close(null, ps, null);
        }
        return 0;
    }

    public <T> int save(T t) {
        return this.save(t, null);
    }

    public <T> int save(T t, SqlPara sqlPara) {
        Class<?> cls = t.getClass();
        Entity entity = this.checkEntity(cls);
        String tableName = this.getTableName(cls, entity, sqlPara);
        if (tableName.trim().isEmpty()) {
            throw new RuntimeException("table is empty");
        }
        String schema = null;
        if (sqlPara != null) {
            schema = sqlPara.getSchema();
        }
        if (schema == null || schema.trim().isEmpty()) {
            schema = entity.schema();
        }
        Connection connection = DbConfig.getConn(this.dbName);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Field[] fields = cls.getDeclaredFields();
            List<Object> valueList = new ArrayList<>();
            List<String> columnList = new ArrayList<>();
            String idFieldName = null;
            Id id = null;
            Class<?> idFieldType = null;
            for (Field field : fields) {
                String name = field.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = this.getMethod("get" + name, cls);
                if (method == null) {
                    method = this.getMethod("is" + name, cls);
                    if (method == null) continue;
                }
                try {
                    Object invoke = method.invoke(t);
                    boolean isIdAuto = false;
                    if (id == null) {
                        id = field.getDeclaredAnnotation(Id.class);
                        if (id != null && id.type() == Id.IdType.AUTO) {
                            idFieldName = name;
                            idFieldType = field.getType();
                            isIdAuto = true;
                        }
                    }
                    if (invoke != null && !isIdAuto) {
                        valueList.add(invoke);
                        columnList.add(this.fieldToColumn(name));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("get value error, cls: " + cls + ", field: " + field.getName());
                }
            }
            if (columnList.isEmpty()) {
                throw new RuntimeException("no update columns");
            }
            StringBuilder stringBuilder = new StringBuilder("insert into ");
            if (schema != null && !schema.trim().isEmpty()) {
                stringBuilder.append("`").append(schema).append("`.");
            }
            stringBuilder.append(tableName).append("(");
            StringBuilder valueBuilder = new StringBuilder();
            for (String string : columnList) {
                stringBuilder.append(string).append(", ");
                valueBuilder.append("?, ");
            }
            stringBuilder.setLength(stringBuilder.length() - 2);
            stringBuilder.append(")");
            valueBuilder.setLength(valueBuilder.length() - 2);
            stringBuilder.append(" values(").append(valueBuilder).append(")");
            ps = connection.prepareStatement(stringBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
            this.setParam(ps, valueList, false);
            int i = ps.executeUpdate();
            if (i > 1) {
                connection.rollback();
            } else {
                if (idFieldName != null && id != null && id.type() == Id.IdType.AUTO) {
                    Method method = this.getMethod("set" + idFieldName, cls, idFieldType);
                    if (method != null) {
                        rs = ps.getGeneratedKeys();
                        if (rs.next()) {
                            Object idValue = rs.getObject(1, idFieldType);
                            try {
                                method.invoke(t, idValue);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                System.err.println("set id error: " + e.getMessage());
                            }
                        }
                    }
                }
            }
            return i;
        } catch (SQLException e) {
            System.err.println("load data error: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("rollback error");
            }
        } finally {
            DbConfig.close(null, ps, rs);
        }
        return 0;
    }

    private Entity checkEntity(Class<?> cls) {
        Entity entity = cls.getDeclaredAnnotation(Entity.class);
        if (entity == null) {
            throw new RuntimeException("@Entity first");
        }
        return entity;
    }

    private Method getMethod(String methodName, Class<?> cls, Class<?>... parameters) {
        try {
            return cls.getDeclaredMethod(methodName, parameters);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private String fieldToColumn(String fieldName) {
        return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1).replaceAll("([A-Z])", "_$1").toLowerCase();
    }

    private <T> T getBeanFromResultSet(Class<T> cls, ResultSet resultSet) {
        T newT = null;
        try {
            newT = cls.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return newT;
        }
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String column = name.replaceAll("(?=[A-Z])", "_").toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            name = "set" + name;
            try {
                Method method = getMethod(name, cls, field.getType());
                if (method != null) {
                    method.invoke(newT, resultSet.getObject(column, field.getType()));
                }
            } catch (Exception e) {
//                System.err.println(name + " error: " + e.getMessage());
            }
        }
        return newT;
    }

    private void setParam(PreparedStatement preparedStatement, List<Object> objects, boolean allowNullValue) throws SQLException {
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            if (object == null && !allowNullValue) {
                throw new RuntimeException("value at " + (i + 1) + " is null");
            }
            preparedStatement.setObject(i + 1, object);
        }
    }

    public <T> T execTransaction(Supplier<T> supplier) {
        Connection connection = DbConfig.startTransaction(this.dbName);
        try {
            T t = supplier.get();
            connection.commit();
            return t;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("transaction error: " + ex.getMessage());
            }
        } finally {
            DbConfig.stopTransaction();
        }
        return null;
    }

    public PageInfo loadPage(SqlPara sqlPara, Map<String, Object> params) {
        PageInfo pageInfo = new PageInfo();

        return pageInfo;
    }
}
