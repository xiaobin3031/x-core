package com.xiaobin.core.server;

import com.sun.net.httpserver.HttpExchange;
import com.xiaobin.core.json.JSON;
import com.xiaobin.core.log.SysLogUtil;
import com.xiaobin.core.server.exception.HttpServerException;
import com.xiaobin.core.server.model.MultiFile;
import com.xiaobin.core.server.model.RequestInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author xuweibin
 * @date 1/13/25
 * @description
 */
class Resolver {

    private final HttpExchange exchange;
    private final RequestInfo requestInfo;
    private final JSON json = new JSON();

    Resolver(HttpExchange exchange, RequestInfo requestInfo){
        this.requestInfo = requestInfo;
        this.exchange = exchange;
    }

    public Object resolve(){
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        Method method = requestInfo.method();
        Object[] values;
        if("application/json".equals(contentType)){
            Parameter[] parameters = method.getParameters();
            values = new Object[parameters.length];
            if(parameters.length > 0){
                Object o = json.withSource(exchange.getRequestBody()).readObject(parameters[0].getType());
                values[0] = o;
            }
        }else if("multipart/form-data".equals(contentType)){
            MultiFile multiFile = new MultiFile();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try(InputStream inputStream = exchange.getRequestBody()){
                byte[] bytes = new byte[1024];
                int read;
                while((read = inputStream.read(bytes)) != -1){
                    outputStream.write(bytes, 0, read);
                }
                // todo 解析文件
            }catch(IOException e){
                throw new HttpServerException("读取文件失败");
            }
            values = new Object[]{multiFile};
        }else{
            throw new HttpServerException("unsupported contentType: " + contentType);
        }
        try {
            return method.invoke(requestInfo.instance(), values);
        } catch (IllegalAccessException | InvocationTargetException e) {
            SysLogUtil.logError(e.getMessage(), e);
            throw new HttpServerException("服务错误");
        }
    }
}
