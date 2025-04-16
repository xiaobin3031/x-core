package com.xiaobin.core.toast;

import com.xiaobin.core.json.JSON;
import com.xiaobin.core.log.SysLogUtil;
import com.xiaobin.core.server.MyHttpHandler;
import com.xiaobin.core.toast.model.ToastModel;

import javax.swing.*;
import javax.swing.text.*;
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
            level_debug, "Debug",
            level_warn, "Warn",
            level_error, "Error"
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
    private final Map<Integer, Integer> levelCountMap = new HashMap<>();

    private int hour;
    private int minute;
    private int seconds;
    private int msgCount = 0;
    private JCheckBox debugCheckBox;
    private JCheckBox warnCheckBox;
    private JCheckBox errorCheckBox;

    private Toast() {
        init();
    }

    private JCheckBox buildCheckBox(int level) {
        JCheckBox checkBox = new JCheckBox(levelMap.get(level));
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                curLevel.add(level);
            } else {
                curLevel.remove(level);
            }
            refreshMsgPanel();
        });
        return checkBox;
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
        debugCheckBox = buildCheckBox(level_debug);
        warnCheckBox = buildCheckBox(level_warn);
        errorCheckBox = buildCheckBox(level_error);
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
            clearAllMsg();
            refreshMsgPanel();
        });
        btnPanel.add(jClearBtn);
        JButton jCancelBtn = new JButton("Cancel");
        jCancelBtn.addActionListener(e -> {
            System.out.println("click cancel");
            clearMsgPanel();
            clearAllMsg();
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

    private void clearAllMsg() {
        debugCheckBox.setText(levelMap.get(level_debug));
        warnCheckBox.setText(levelMap.get(level_warn));
        errorCheckBox.setText(levelMap.get(level_error));
        curLevel.clear();
        msgList.clear();
        msgLevelList.clear();
        levelCountMap.clear();
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

    private void setLogLevelStyle(Style style, int level) {
        if (level == level_warn) {
            StyleConstants.setForeground(style, Color.ORANGE);
        } else if (level == level_error) {
            StyleConstants.setForeground(style, Color.RED);
        }
    }

    private void addToPane(int level, String message) {
        JTextArea jText = new JTextArea();
        jText.setEditable(false);
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        Style style = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        Document doc = jText.getDocument();
        insertString(doc, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), style);
        setLogLevelStyle(style, level);
        insertString(doc, " " + levelMap.get(level), style);
        insertString(doc, " " + message, style);
        msgLevelList.add(level);
        msgList.add(jText);
        int count = levelCountMap.getOrDefault(level, 0);
        count++;
        levelCountMap.put(level, count);
        refreshLevelCount(level, count);
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
        SysLogUtil.logNormalF("show message [%s]: %s\n", levelMap.get(level), message);
    }

    private void insertString(Document doc, String msg, Style style) {
        try {
            doc.insertString(doc.getLength(), msg, style);
        } catch (BadLocationException e) {
            SysLogUtil.logError(e.getMessage());
        }
    }

    private void refreshLevelCount(int level, int count) {
        switch (level) {
            case level_debug -> debugCheckBox.setText(String.format("Debug: %d", count));
            case level_warn -> warnCheckBox.setText(String.format("Warn: %d", count));
            case level_error -> errorCheckBox.setText(String.format("Error: %d", count));
        }
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
