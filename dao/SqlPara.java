package com.xiaobin.core.dao;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * created by xuweibin at 2024/8/20 16:24
 */
public class SqlPara {

    private final StringBuilder whereBuilder = new StringBuilder();
    private final StringBuilder orderBy = new StringBuilder();
    @Getter
    private final List<Object> values = new ArrayList<>();
    private String lastSql;
    private int offset = 0;
    private int size = 0;
    private String plainWhere;
    private String groupBy;

    @Getter
    private String tableName;
    @Getter
    private String schema;

    public SqlPara() {
    }

    public SqlPara(String tableName, String schema) {
        this.tableName = tableName;
        this.schema = schema;
    }

    public SqlPara setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SqlPara setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public SqlPara eq(String column, Object value) {
        if (value != null) {
            this.appendWithValue(column, "=");
            this.values.add(value);
        }
        return this;
    }

    public SqlPara gt(String column, Object value) {
        if (value != null) {
            this.appendWithValue(column, " > ");
            this.values.add(value);
        }
        return this;
    }

    public SqlPara ge(String column, Object value) {
        if (value != null) {
            this.appendWithValue(column, " >= ");
            this.values.add(value);
        }
        return this;
    }

    public SqlPara lt(String column, Object value) {
        if (value != null) {
            this.appendWithValue(column, " < ");
            this.values.add(value);
        }
        return this;
    }

    public SqlPara ne(String column, Object value) {
        if (value != null) {
            this.appendWithValue(column, "!=");
            this.values.add(value);
        }
        return this;
    }

    public SqlPara between(String col1, String col2, Object value) {
        if (value != null) {
            if (!this.whereBuilder.isEmpty()) {
                this.whereBuilder.append(" and ");
            }
            this.whereBuilder.append(" ? between ").append(col1).append(" and ").append(col2);
            this.values.add(value);
        }
        return this;
    }

    public SqlPara isNull(String column) {
        this.append(column, "is null");
        return this;
    }

    public SqlPara isNotNull(String column) {
        this.append(column, "is not null");
        return this;
    }

    public SqlPara like(String column, Object value) {
        if (value != null) {
            this.appendWithValue(column, "like");
            this.values.add(value);
        }
        return this;
    }

    public SqlPara likeRight(String column, Object value) {
        if (value != null) {
            this.appendWithValue(column, "like");
            this.values.add(value + "%");
        }
        return this;
    }

    public SqlPara likeLeft(String column, Object value) {
        if (value != null) {
            this.appendWithValue(column, "like");
            this.values.add("%" + value);
        }
        return this;
    }

    public SqlPara last(String sql) {
        this.lastSql = sql;
        return this;
    }

    public SqlPara page(int page, int size) {
        if (page > 0) this.offset = (page - 1) * size;
        if (size >= 0) this.size = size;
        return this;
    }

    public SqlPara orderBy(String column) {
        if (column != null && !column.isEmpty()) {
            if (!this.orderBy.isEmpty()) {
                this.orderBy.append(", ");
            } else {
                this.orderBy.append(" order by ");
            }
            this.orderBy.append(column);
        }
        return this;
    }

    public SqlPara orderByDesc(String column) {
        if (column != null && !column.isEmpty()) {
            if (!this.orderBy.isEmpty()) {
                this.orderBy.append(", ");
            } else {
                this.orderBy.append(" order by ");
            }
            this.orderBy.append(column).append(" desc");
        }
        return this;
    }

    public SqlPara in(String column, Collection<?> value) {
        if (value == null) {
            throw new RuntimeException("value must not null");
        }
        if (!this.whereBuilder.isEmpty()) {
            this.whereBuilder.append(" and ");
        }
        this.whereBuilder.append(column).append(" in (");
        for (Object o : value) {
            this.whereBuilder.append("?,");
            this.values.add(o);
        }
        if (this.whereBuilder.charAt(this.whereBuilder.length() - 1) == ',') {
            this.whereBuilder.replace(this.whereBuilder.length() - 1, this.whereBuilder.length(), ")");
        } else {
            this.whereBuilder.append(")");
        }
        return this;
    }

    public SqlPara notIn(String column, List<?> value) {
        if (value == null) {
            throw new RuntimeException("value must not null");
        }
        if (!this.whereBuilder.isEmpty()) {
            this.whereBuilder.append(" and ");
        }
        this.whereBuilder.append(column).append(" not in (");
        for (Object o : value) {
            this.whereBuilder.append("?,");
            this.values.add(o);
        }
        if (this.whereBuilder.charAt(this.whereBuilder.length() - 1) == ',') {
            this.whereBuilder.replace(this.whereBuilder.length() - 1, this.whereBuilder.length(), ")");
        } else {
            this.whereBuilder.append(")");
        }
        return this;
    }

    public SqlPara groupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public SqlPara plainWhere(String plainWhere) {
        if (plainWhere != null && !plainWhere.isEmpty()) {
            this.whereBuilder.append(plainWhere);
        }
        return this;
    }

    private void append(String column, String operator) {
        if (!this.whereBuilder.isEmpty()) {
            this.whereBuilder.append(" and ");
        }
        this.whereBuilder.append(column).append(" ").append(operator).append(" ");
    }

    private void appendWithValue(String column, String operator) {
        if (!this.whereBuilder.isEmpty()) {
            this.whereBuilder.append(" and ");
        }
        this.whereBuilder.append(column).append(" ").append(operator).append(" ?");
    }

    public String getWhere() {
        if (this.plainWhere != null) return this.plainWhere;

        this.plainWhere = "";
        if (!this.whereBuilder.isEmpty()) {
            this.plainWhere = " where " + this.whereBuilder;
        }

        if (!this.orderBy.isEmpty()) {
            this.plainWhere += " " + this.orderBy;
        }
        if (this.groupBy != null && !this.groupBy.isEmpty()) {
            this.plainWhere += " group by " + this.groupBy;
        }
        if (this.lastSql != null && !this.lastSql.isEmpty()) {
            this.plainWhere += " " + this.lastSql;
        }
        if (this.size > 0 && this.offset >= 0) {
            this.plainWhere += " limit " + this.offset + ", " + this.size;
        }
        return this.plainWhere;
    }

    public String toSql(String tableName, String schema) {
        if (tableName == null || tableName.trim().isEmpty()) {
            throw new RuntimeException("tableName is empty");
        }
        StringBuilder stringBuilder = new StringBuilder("select * from ");
        if (schema != null && !schema.trim().isEmpty()) {
            stringBuilder.append("`").append(schema).append("`.");
        }
        stringBuilder.append(tableName);
        String where = this.getWhere();
        stringBuilder.append(where);
        return stringBuilder.toString();
    }
}
