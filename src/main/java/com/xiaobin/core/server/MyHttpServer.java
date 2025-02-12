package com.xiaobin.core.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.xiaobin.core.bean.BeanManager;
import com.xiaobin.core.json.JSON;
import com.xiaobin.core.log.SysLogUtil;
import com.xiaobin.core.server.config.Get;
import com.xiaobin.core.server.config.Post;
import com.xiaobin.core.server.config.Request;
import com.xiaobin.core.server.config.RequestParam;
import com.xiaobin.core.server.exception.HttpServerException;
import com.xiaobin.core.server.model.HttpResponse;
import com.xiaobin.core.server.model.RequestInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * created by xuweibin at 2024/11/20 16:00
 */
public class MyHttpServer {

    private static HttpServer server;
    private static final Map<String, MyHttpHandler<?>> HTTP_HANDLER_MAP = new HashMap<>();

    private static final RequestHandConfig requestHandConfig = new RequestHandConfig();
    private final JSON json = new JSON();

    public static void createServer(int port, Object... requestHandler) {
        registerHandler(requestHandler);
        createServer(port);
    }

    private static void registerHandler(Object... requestHandlers) {
        if (requestHandlers == null || requestHandlers.length == 0) {
            throw new HttpServerException("request handler is empty");
        }

        for (Object requestHandler : requestHandlers) {
            Class<?> aClass = requestHandler.getClass();
            Annotation[] annotations = aClass.getAnnotations();
            String prefixPath = "";
            for (Annotation annotation : annotations) {
                if (annotation instanceof Request request) {
                    prefixPath = request.value();
                }
            }

            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                Get get = method.getAnnotation(Get.class);
                if (get != null) {
                    String path = get.value();
                    path = prefixPath + "/" + path;
                    requestHandConfig.defHandler(path, method, "get", requestHandler);
                }
                Post post = method.getAnnotation(Post.class);
                if (post != null) {
                    String path = post.value();
                    path = prefixPath + "/" + path;
                    requestHandConfig.defHandler(path, method, "post", requestHandler);
                }
            }
        }
    }

    public static void createServer(int port) {
        createServer(port, false);
    }

    private static void createServer(int port, boolean focusShutdown) {
        if (server != null) {
            if (!focusShutdown) return;
            System.out.println("out of time, restart...");
            server.stop(1);
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                System.out.println("interrupted exception: " + e.getMessage());
            }
        }
        if (port <= 0) port = 6666;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.createContext("/", new InnerHttpHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("notice server start success...");
    }

    public static void addContext(String path, MyHttpHandler<?> handler) {
        Objects.requireNonNull(handler, "Http handler must not null");
        if (HTTP_HANDLER_MAP.containsKey(path)) {
            System.out.println("handler of path already exist: " + path);
        } else {
            HTTP_HANDLER_MAP.put(path, handler);
            System.out.println("create context path: " + path);
        }
    }

    private static class InnerHttpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uriString = exchange.getRequestURI().toString();
            // 设置响应头
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            // chrome 需要
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
            String result;
            HttpResponse<Object> response = HttpResponse.builder().build();
            try {
                if ("get".equalsIgnoreCase(exchange.getRequestMethod()) || "post".equalsIgnoreCase(exchange.getRequestMethod())) {
//                MyHttpHandler<?> handler = requestHandConfig.getHandler(uriString, exchange.getRequestMethod());
                    int index = uriString.indexOf("?");
                    String path;
                    if (index == -1) {
                        path = uriString;
                    } else {
                        path = uriString.substring(0, index);
                    }
                    RequestInfo handler = requestHandConfig.getHandler(path, exchange.getRequestMethod());
                    if (handler == null) {
                        response.setCode(0);
                        response.setMsg("Success");
                        response.setData("Hello World!");
                    } else {
                        Parameter[] parameters = handler.method().getParameters();
                        Object[] params = new Object[parameters.length];
                        try {
                            if ("get".equalsIgnoreCase(exchange.getRequestMethod())) {
                                String query = exchange.getRequestURI().getQuery();
                                if (query != null) {
                                    Map<String, String> queryParams = new HashMap<>();
                                    for (String s : query.split("&")) {
                                        String[] split = s.split("=");
                                        if (split[1] != null) {
                                            String decode = URLDecoder.decode(split[1], StandardCharsets.UTF_8);
                                            queryParams.put(split[0], decode);
                                        }
                                    }
                                    for (int i = 0; i < parameters.length; i++) {
                                        Parameter parameter = parameters[i];
                                        RequestParam annotation = parameter.getAnnotation(RequestParam.class);
                                        if (annotation != null && annotation.value() != null) {
                                            params[i] = BeanManager.convertValue(queryParams.get(annotation.value()), parameter.getType());
                                        }
                                    }
                                }
                            } else {
                                try (InputStream requestBody = exchange.getRequestBody()) {
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    byte[] bytes = new byte[1024];
                                    int read;
                                    while ((read = requestBody.read(bytes)) > -1) {
                                        byteArrayOutputStream.write(bytes, 0, read);
                                    }
                                    JSON json = new JSON();
                                    for (int i = 0; i < parameters.length; i++) {
                                        params[i] = json.withSource(byteArrayOutputStream.toString(StandardCharsets.UTF_8)).readObject(parameters[i].getType());
                                    }
                                }
                            }

                            Object value = handler.method().invoke(handler.instance(), params);
                            response.setCode(0);
                            response.setMsg("Success");
                            response.setData(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                            SysLogUtil.logError(e.getMessage());
                            response.setCode(-1);
                            response.setMsg(e.getMessage());
                        }
                    }
                } else {
                    response.setMsg("Hello World!");
                }
            } catch (Exception e) {
                response.setMsg(e.getMessage());
                response.setCode(500);
            } finally {
                JSON json = new JSON();
                result = json.parse(response);
                byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Length", String.valueOf(bytes.length));
                exchange.sendResponseHeaders(200, 0);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(bytes);
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}
