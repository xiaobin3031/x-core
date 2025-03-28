package com.xiaobin.core.toast;

import com.xiaobin.core.json.JSON;
import com.xiaobin.core.log.SysLogUtil;
import com.xiaobin.core.server.MyHttpHandler;
import com.xiaobin.core.server.MyHttpServer;
import com.xiaobin.core.toast.model.ToastModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * created by xuweibin at 2024/11/15 13:38
 * 消息提醒
 */
public class Toast {

    private static final int level_debug = 1;
    private static final int level_warn = 100;
    private static final int level_error = 200;

    private static final Map<Integer, String> levelMap = Map.of(
            level_debug, "debug",
            level_warn, "warn",
            level_error, "error"
    );

    private final static Toast instance = new Toast();

    private JFrame TOAST_FRAME;
    private JPanel MSG_PANEL;
    private final JLabel hourField = new JLabel();
    private final JLabel minuteField = new JLabel();
    private final JLabel secondField = new JLabel();
    private final JLabel ymdField = new JLabel();
    private final Set<Integer> curLevel = new HashSet<>();

    private final List<Integer> msgLevelList = new ArrayList<>();
    private final List<JTextArea> msgList = new ArrayList<>();

    private int hour;
    private int minute;
    private int seconds;

    private int msgCount = 0;

    static {
        MyHttpServer.addContext("/notice", new ToastHttpHandle());
    }

    private Toast(){
        init();
    }

    private void init() {
        JFrame jFrame = new JFrame("toast");
        jFrame.setLocationRelativeTo(null);
        jFrame.setSize(350, 200);
        jFrame.setLayout(new GridLayout(2, 1));

        Container container = jFrame.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new FlowLayout());
        JCheckBox debugCheckBox = new JCheckBox("debug");
        debugCheckBox.addActionListener(e -> {
            if (debugCheckBox.isSelected()) {
                curLevel.add(level_debug);
            } else {
                curLevel.remove(level_debug);
            }
            refreshMsgPanel();
        });
        JCheckBox warnCheckBox = new JCheckBox("warn");
        warnCheckBox.addActionListener(e -> {
            if (warnCheckBox.isSelected()) {
                curLevel.add(level_warn);
            } else {
                curLevel.remove(level_warn);
            }
            refreshMsgPanel();
        });
        JCheckBox errorCheckBox = new JCheckBox("error");
        errorCheckBox.addActionListener(e -> {
            if (errorCheckBox.isSelected()) {
                curLevel.add(level_error);
            } else {
                curLevel.remove(level_error);
            }
            refreshMsgPanel();
        });
        levelPanel.add(debugCheckBox);
        levelPanel.add(warnCheckBox);
        levelPanel.add(errorCheckBox);
        container.add(levelPanel, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        JPanel msgPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(20, 1);
        msgPanel.setLayout(gridLayout);
        JScrollPane jScrollPane = new JScrollPane(msgPanel);
        jScrollPane.setAutoscrolls(true);
        center.add(jScrollPane, BorderLayout.CENTER);

        JPanel btnPanel = initBtnPanel();
        center.add(btnPanel, BorderLayout.SOUTH);
        container.add(center, BorderLayout.CENTER);

        JPanel toolBar = initToolbar();
        container.add(toolBar, BorderLayout.SOUTH);
        MSG_PANEL = msgPanel;
        TOAST_FRAME = jFrame;
    }

    private void refreshMsgPanel() {
        clearMsgPanel();
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < msgLevelList.size(); i++) {
            if (matchLevel(msgLevelList.get(i))) {
                indexList.add(i);
            }
        }
        msgCount = indexList.size();
        if (msgCount == 0) {
            msgCount++;
        }
        GridLayout layout = (GridLayout) MSG_PANEL.getLayout();
        layout.setRows(msgCount);
        for (Integer i : indexList) {
            MSG_PANEL.add(msgList.get(i));
        }
        MSG_PANEL.repaint();
    }

    private boolean matchLevel(int level) {
        return curLevel.isEmpty() || curLevel.contains(level);
    }

    private JPanel initBtnPanel() {
        JPanel btnPanel = new JPanel();
        JButton jClearBtn = new JButton("Clear");
        jClearBtn.addActionListener(e -> {
            clearMsgPanel();
        });
        btnPanel.add(jClearBtn);
        JButton jCancelBtn = new JButton("Cancel");
        jCancelBtn.addActionListener(e -> {
            System.out.println("click cancel");
            clearMsgPanel();
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

    private void clearMsgPanel() {
        for (Component component : MSG_PANEL.getComponents()) {
            MSG_PANEL.remove(component);
        }
        msgCount = 0;
    }

    private JPanel initToolbar() {
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

    private void refreshYmd() {
        ymdField.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE) + " ");
    }

    private void setTimeField(JLabel jLabel, int time) {
        if (time < 10) {
            jLabel.setText("0" + time);
        } else {
            jLabel.setText(time + "");
        }
    }

    public static void show(String message) {
        instance.addToPane(level_debug, message);
    }

    public static void showDebug(String message) {
        instance.addToPane(level_debug, message);
    }

    public static void showWarn(String message) {
        instance.addToPane(level_warn, message);
    }

    public static void showError(String message) {
        instance.addToPane(level_error, message);
    }

    private void addToPane(int level, String message) {
        String s = formatMsg(level, message);
        JTextArea jText = new JTextArea(s);
        jText.setEditable(false);
        msgLevelList.add(level);
        msgList.add(jText);
        if (matchLevel(level)) {
            GridLayout layout = (GridLayout) MSG_PANEL.getLayout();
            if (layout.getRows() <= msgCount) {
                layout.setRows(layout.getRows() + 10);
            }
            MSG_PANEL.add(jText);
            msgCount++;
        }
        if (!TOAST_FRAME.isVisible()) {
            TOAST_FRAME.setVisible(true);
        }
        SysLogUtil.logNormalF("show message [%s]: %s", levelMap.get(level), message);
    }

    private String formatMsg(String msg) {
        if (msg != null) {
            msg = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "   " + msg;
        }
        return msg;
    }

    private String formatMsg(int level, String msg) {
        if (msg != null) {
            msg = String.format("%s [%s]   %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), levelMap.get(level), msg);
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
