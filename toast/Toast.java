package com.xiaobin.core.toast;

import com.xiaobin.core.json.JSON;
import com.xiaobin.core.toast.model.ToastModel;
import com.xiaobin.core.server.MyHttpHandler;
import com.xiaobin.core.server.MyHttpServer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * created by xuweibin at 2024/11/15 13:38
 * 消息提醒
 */
public class Toast {

    private static JFrame TOAST_FRAME;
    private static JTextArea TOAST_MSG_TEXT_AREA;

    static {
        init();

        MyHttpServer.addContext("/notice", new ToastHttpHandle());
    }

    private static void init() {
        JFrame jFrame = new JFrame("toast");
        jFrame.setLocationRelativeTo(null);
        jFrame.setSize(350, 200);
        jFrame.setLayout(new GridLayout(2, 1));

        JTextArea msgTextArea = new JTextArea();
        msgTextArea.setText("11111");
        msgTextArea.setEditable(false);
        JScrollPane msgPanel = new JScrollPane(msgTextArea);
        jFrame.add(msgPanel);

        JPanel btnPanel = new JPanel();
        JButton jClearBtn = new JButton("Clear");
        jClearBtn.addActionListener(e -> {
            msgTextArea.setText("");
        });
        btnPanel.add(jClearBtn);
        JButton jCancelBtn = new JButton("Cancel");
        jCancelBtn.addActionListener(e -> {
            System.out.println("click cancel");
            jFrame.setVisible(false);
        });
        btnPanel.add(jCancelBtn);
        JButton jOkBtn = new JButton("Ok");
        jOkBtn.addActionListener(e -> {
            System.out.println("click ok");
            jFrame.setVisible(false);
        });
        btnPanel.add(jOkBtn);
        jFrame.add(btnPanel);

        TOAST_MSG_TEXT_AREA = msgTextArea;
        TOAST_FRAME = jFrame;
    }

    private final ToastModel model;

    public Toast(ToastModel model) {
        this.model = model;
    }

    public void show() {
        if (this.model == null || this.model.getMsg() == null) return;

        TOAST_MSG_TEXT_AREA.requestFocus();
        if (TOAST_FRAME.isVisible()) {
            String msg = TOAST_MSG_TEXT_AREA.getText();
            msg += "\r\n" + this.formatMsg(this.model.getMsg());
            TOAST_MSG_TEXT_AREA.setText(msg);
        } else {
            TOAST_MSG_TEXT_AREA.setText(this.formatMsg(this.model.getMsg()));
            if (this.model.getTitle() != null) {
                TOAST_FRAME.setTitle(this.model.getTitle());
            }
            TOAST_FRAME.setVisible(true);
        }
        System.out.println("show message: " + this.model.getMsg());
    }

    public void show(String message) {
        if (TOAST_FRAME.isVisible()) {
            String msg = TOAST_MSG_TEXT_AREA.getText();
            msg += "\r\n" + this.formatMsg(message);
            TOAST_MSG_TEXT_AREA.setText(msg);
        } else {
            TOAST_MSG_TEXT_AREA.setText(this.formatMsg(message));
            TOAST_FRAME.setVisible(true);
        }
        System.out.println("show message: " + message);
    }

    private String formatMsg(String msg) {
        if (msg != null) {
            msg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "   " + msg;
        }
        return msg;
    }

    private static class ToastHttpHandle implements MyHttpHandler<Object> {

        private final AtomicLong clientCount = new AtomicLong(0);

        @Override
        public Object handle(String body) {
            long clientId = clientCount.incrementAndGet();
            System.out.println("toast client connect, index: " + clientId);
            ToastModel model = new JSON().withSource(body).readObject(ToastModel.class);
            System.out.println("toast client id: " + clientId + ", request body: " + body);
            new Toast(model).show();
            return null;
        }
    }
}
