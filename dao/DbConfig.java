package com.xiaobin.core.dao;

import com.xiaobin.core.json.JSON;
import com.xiaobin.core.dao.model.DbConnModel;
import com.xiaobin.core.log.SysLogUtil;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * created by xuweibin at 2024/11/20 10:33
 */
public class DbConfig {

    private final static Map<String, DbConnModel> DB_CONN_MODEL_MAP = new HashMap<>();

    private final static ThreadLocal<Map<String, Connection>> CONNECTION_THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    private final static ThreadLocal<Map<String, Connection>> TRANSACTION_CONNECTION_THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("数据库连接初始化失败: " + e.getMessage());
            throw new RuntimeException(e);
        }

        File configFile = new File("E:\\文档\\db-config.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            JSON json = new JSON();
            while ((line = reader.readLine()) != null) {
                int index = line.indexOf(":");
                if (index == -1) {
                    continue;
                }
                String name = line.substring(0, index);
                String configString = line.substring(index + 1);
                DbConnModel dbConnModel = json.withSource(configString).readObject(DbConnModel.class);
                DB_CONN_MODEL_MAP.put(name, dbConnModel);
            }
        } catch (IOException e) {
            System.out.println("db config load error");
            throw new RuntimeException(e);
        }
    }

    public static Connection getConn(String name) {
        DbConnModel dbConnModel = DB_CONN_MODEL_MAP.get(name);
        if (dbConnModel == null) throw new RuntimeException("db config not found with name: " + name);

        Connection connection = TRANSACTION_CONNECTION_THREAD_LOCAL.get().get(name);
        if (connection == null) {
            connection = CONNECTION_THREAD_LOCAL.get().get(name);
        }

        if (connection != null) {
            try {
                if (connection.isClosed()) {
                    connection = null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (connection == null) {
            SysLogUtil.logWarn("create connect from driver manager");
            try {
                connection = DriverManager.getConnection(dbConnModel.getUrl(), dbConnModel.getUsername(), dbConnModel.getPassword());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            CONNECTION_THREAD_LOCAL.get().put(name, connection);
        }
        return connection;
    }

    public static Connection startTransaction(String name) {
        TRANSACTION_CONNECTION_THREAD_LOCAL.remove();
        Connection connection = getConn(name);

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        TRANSACTION_CONNECTION_THREAD_LOCAL.get().put(name, connection);

        return connection;
    }

    public static void stopTransaction() {
        TRANSACTION_CONNECTION_THREAD_LOCAL.remove();
    }

    public static void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("close rs error");
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println("close ps error");
            }
        }
        if (connection != null) {
            try {
                connection.close();
                CONNECTION_THREAD_LOCAL.remove();
            } catch (SQLException e) {
                System.out.println("close connection error");
            }
        }
    }
}
