package com.xiaobin.core.dao.service;

import com.xiaobin.core.dao.SqlFactory;
import com.xiaobin.core.dao.SqlPara;
import com.xiaobin.core.dao.annotation.Entity;
import com.xiaobin.core.data.VersionData;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * created by xuweibin at 2024/11/22 20:47
 */
public class DaoServiceProxy implements InvocationHandler {

    private final SqlFactory sqlFactory;
    private final VersionData versionData;

    public DaoServiceProxy(String dbName, VersionData versionData) {
        this.sqlFactory = new SqlFactory(dbName);
        this.versionData = versionData;
    }

    public DaoServiceProxy(SqlFactory sqlFactory, VersionData versionData) {
        this.sqlFactory = sqlFactory;
        this.versionData = versionData;
    }

    public DaoService getService() {
        return (DaoService) Proxy.newProxyInstance(DaoService.class.getClassLoader(), new Class[]{DaoService.class}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Objects.requireNonNull(args);
        SqlPara sqlPara = new SqlPara();
        if (args[0] instanceof Class<?> cls) {
            StringBuilder keyBuilder = new StringBuilder();
            for (Object arg : args) {
                if (arg != null) {
                    keyBuilder.append(arg).append(0x10);
                }
            }
            String key = keyBuilder.toString();
            Object data = null;
            if (this.versionData != null) {
                data = this.versionData.getData(key);
            }
            if (data != null) {
                return data;
            }
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
                if("loadByPIdAndDeleted".equals(methodName) && args.length >= 2 && args[1] instanceof Long arg1 && arg1 == 0){
                    // 先临时改一下
                    return Collections.emptyList();
                }
                int offset = 0;
                boolean loadOne = false;
                if (methodName.startsWith("loadBy")) {
                    offset = 6;
                } else if (methodName.startsWith("loadOneBy")) {
                    offset = 9;
                    loadOne = true;
                }
                if (offset > 0) {
                    String params = methodName.substring(offset);
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
                    List<?> list = this.sqlFactory.load(cls, sqlPara);
                    if (loadOne) {
                        if (list.size() == 1) {
                            data = list.get(0);
                        } else if (!list.isEmpty()) {
                            throw new RuntimeException("want one record, but get " + list.size());
                        }
                    } else {
                        data = list;
                    }
                    if (this.versionData != null && data != null) {
                        this.versionData.setData(key, data);
                    }
                    return data;
                }
            }
        } else {
            System.out.println("first arg must en class");
        }
        throw new RuntimeException("unknown method: " + method.getName());
    }
}
