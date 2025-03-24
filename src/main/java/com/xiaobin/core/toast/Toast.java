package com.xiaobin.core.toast;

import com.xiaobin.core.json.JSON;
import com.xiaobin.core.server.MyHttpHandler;
import com.xiaobin.core.server.MyHttpServer;
import com.xiaobin.core.toast.model.ToastModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * created by xuweibin at 2024/11/15 13:38
 * 消息提醒
 */
public class Toast {

    private static JFrame TOAST_FRAME;
    private static JPanel MSG_PANEL;
    private final static JLabel hourField = new JLabel();
    private final static JLabel minuteField = new JLabel();
    private final static JLabel secondField = new JLabel();
    private final static JLabel ymdField = new JLabel();

    private static int hour;
    private static int minute;
    private static int seconds;

    static {
        init();

        MyHttpServer.addContext("/notice", new ToastHttpHandle());
    }

    private static void init() {
        JFrame jFrame = new JFrame("toast");
        jFrame.setLocationRelativeTo(null);
        jFrame.setSize(350, 200);
        jFrame.setLayout(new GridLayout(2, 1));

        Container container = jFrame.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        JPanel msgPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(100, 1);
        msgPanel.setLayout(gridLayout);
        center.add(new JScrollPane(msgPanel), BorderLayout.CENTER);

        JPanel btnPanel = initBtnPanel();
        center.add(btnPanel, BorderLayout.SOUTH);
        container.add(center, BorderLayout.CENTER);

        JPanel toolBar = initToolbar();
        container.add(toolBar, BorderLayout.SOUTH);
        MSG_PANEL = msgPanel;
        TOAST_FRAME = jFrame;
    }

    private static JPanel initBtnPanel() {
        JPanel btnPanel = new JPanel();
        JButton jClearBtn = new JButton("Clear");
        jClearBtn.addActionListener(e -> {
            MSG_PANEL.removeAll();
        });
        btnPanel.add(jClearBtn);
        JButton jCancelBtn = new JButton("Cancel");
        jCancelBtn.addActionListener(e -> {
            System.out.println("click cancel");
            MSG_PANEL.removeAll();
            TOAST_FRAME.setVisible(false);
        });
        btnPanel.add(jCancelBtn);
        JButton jOkBtn = new JButton("Ok");
        jOkBtn.addActionListener(e -> {
            System.out.println("click ok");
            TOAST_FRAME.setVisible(false);
        });
        btnPanel.add(jOkBtn);

        return btnPanel;
    }

    private static JPanel initToolbar() {
        JPanel toolBar = new JPanel();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        refreshYmd();
        toolBar.add(ymdField);
        toolBar.add(hourField);
        toolBar.add(new JLabel(":"));
        toolBar.add(minuteField);
        toolBar.add(new JLabel(":"));
        toolBar.add(secondField);

        Timer timer = new Timer();
        LocalDateTime now = LocalDateTime.now();
        hour = now.getHour();
        minute = now.getMinute();
        seconds = now.getSecond();
        setTimeField(hourField, hour);
        setTimeField(minuteField, minute);
        setTimeField(secondField, seconds);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if (seconds >= 60) {
                    seconds = 0;
                    minute++;
                    if (minute >= 60) {
                        minute = 0;
                        hour++;
                        if (hour >= 24) {
                            hour = 0;
                            refreshYmd();
                        }
                        setTimeField(hourField, hour);
                    }
                    setTimeField(minuteField, minute);
                }
                setTimeField(secondField, seconds);
            }
        }, 1000, 1000);

        return toolBar;
    }

    private static void refreshYmd() {
        ymdField.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE) + " ");
    }

    private static void setTimeField(JLabel jLabel, int time) {
        if (time < 10) {
            jLabel.setText("0" + time);
        } else {
            jLabel.setText(time + "");
        }
    }

    public static void show(String message) {
        JLabel jLabel = new JLabel(formatMsg(message));
        MSG_PANEL.add(jLabel);
        if (!TOAST_FRAME.isVisible()) {
            TOAST_FRAME.setVisible(true);
        }
        System.out.println("show message: " + message);
    }

    private static String formatMsg(String msg) {
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
            Toast.show(model.getMsg());
            return null;
        }
    }
}
