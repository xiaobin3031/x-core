package com.xiaobin.core.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.xiaobin.core.json.JSON;
import com.xiaobin.core.server.model.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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

    static {
//        java.util.Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                createServer(true);
//            }
//        }, 1000, TimeUnit.MINUTES.toMillis(10));
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
            if ("get".equalsIgnoreCase(exchange.getRequestMethod()) || "post".equalsIgnoreCase(exchange.getRequestMethod())) {
                MyHttpHandler<?> handler = HTTP_HANDLER_MAP.get(uriString);
                if (handler == null) {
                    response.setCode(0);
                    response.setMsg("Success");
                    response.setData("Hello World!");
                } else {
                    try (InputStream requestBody = exchange.getRequestBody()) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int read;
                        while ((read = requestBody.read(bytes)) > -1) {
                            byteArrayOutputStream.write(bytes, 0, read);
                        }
                        Object value = handler.handle(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
                        response.setCode(0);
                        response.setMsg("Success");
                        response.setData(value);
                    } catch (Exception e) {
                        response.setCode(-1);
                        response.setMsg(e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                response.setMsg("Hello World!");
            }
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
