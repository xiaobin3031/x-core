package com.xiaobin.core.dao.service;

import com.xiaobin.core.dao.SqlFactory;
import com.xiaobin.core.dao.SqlPara;
import com.xiaobin.core.dao.annotation.Entity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Objects;

/**
 * created by xuweibin at 2024/11/22 20:47
 */
public class DaoServiceProxy implements InvocationHandler {

    private final SqlFactory sqlFactory;

    public DaoServiceProxy(String dbName) {
        this.sqlFactory = new SqlFactory(dbName);
    }

    public DaoServiceProxy(SqlFactory sqlFactory) {
        this.sqlFactory = sqlFactory;
    }

    public DaoService getService() {
        return (DaoService) Proxy.newProxyInstance(DaoService.class.getClassLoader(), new Class[]{DaoService.class}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Objects.requireNonNull(args);
        SqlPara sqlPara = new SqlPara();
        if (args[0] instanceof Class<?> cls) {
            Entity entity = cls.getDeclaredAnnotation(Entity.class);
            if (entity != null) {
                String tableName;
                if (entity.tableName() != null) {
                    tableName = entity.tableName();
                } else {
                    tableName = cls.getName();
                    tableName = tableName.substring(0, 1).toLowerCase() + tableName.substring(1).replaceAll("[A-Z]", "_\1").toLowerCase();
                }
                sqlPara.setTableName(tableName);
                sqlPara.setSchema(entity.schema());
                String methodName = method.getName();
                if (methodName.startsWith("loadBy")) {
                    String params = methodName.substring(6);
                    int index;
                    int argIndex = 1;
                    while (true) {
                        if (argIndex > args.length) break;
                        index = params.indexOf("And");
                        String param;
                        if (index == -1) {
                            param = params;
                        } else {
                            param = params.substring(0, index);
                        }
                        if (args[argIndex] != null) {
                            String column = param.substring(0, 1).toLowerCase() + param.substring(1).replaceAll("([A-Z])", "_$1").toLowerCase();
                            if (args[argIndex] instanceof Collection<?> list) {
                                sqlPara.in(column, list);
                            } else {
                                sqlPara.eq(column, args[argIndex]);
                            }
                        }
                        argIndex++;
                        if (index == -1) {
                            break;
                        }
                        params = params.substring(index + 3);
                    }
                    return sqlFactory.load(cls, sqlPara);
                }
            }
        } else {
            System.out.println("first arg must en class");
        }
        throw new RuntimeException("unknown method: " + method.getName());
    }
}
