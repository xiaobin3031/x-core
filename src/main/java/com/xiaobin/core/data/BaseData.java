package com.xiaobin.core.data;

import com.xiaobin.core.json.JSON;
import com.xiaobin.core.log.SysLogUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * created by xuweibin at 2024/8/1 18:21
 */
public class BaseData<T> {

    public static final File ROOT_FOLD;
    private final JSON json = new JSON();
    private final String[] filename;
    private final boolean storeToFileFlag;
    private Class<T> cls;

    static {

        String path = getUserPath();
        path += File.separator + "datas-prop";
        ROOT_FOLD = new File(path);
        if (!ROOT_FOLD.exists()) {
            if (!ROOT_FOLD.mkdirs()) {
                throw new RuntimeException("error create dirs: " + path);
            }
        }
    }

    private static String getUserPath() {
        String os = System.getenv("OS");
        String path;
        if (os != null && os.toLowerCase().contains("windows")) {
            path = System.getenv("USERPROFILE");
        } else {
            path = System.getenv("HOME");
        }
        SysLogUtil.logWarn("use path: " + path);
        return path;
    }

    public static File getUserFile(String... filenames) {
        String path = getUserPath();
        File dir = new File(path);
        for (int i = 0; i < filenames.length - 1; i++) {
            dir = new File(dir, filenames[i]);
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    throw new RuntimeException("fold create error, fold: " + dir.getAbsolutePath());
                }
            }
        }
        return new File(dir, filenames[filenames.length - 1]);
    }

    protected BaseData(boolean storeToFileFlag, String... filename) {
        this.storeToFileFlag = storeToFileFlag;
        if (this.storeToFileFlag) {
            Objects.requireNonNull(filename);
            this.filename = filename;
        } else {
            this.filename = new String[0];
        }
    }

    protected BaseData(boolean storeToFileFlag, Class<T> cls, String... filename) {
        this.cls = cls;
        this.storeToFileFlag = storeToFileFlag;
        if (this.storeToFileFlag) {
            Objects.requireNonNull(filename);
            this.filename = filename;
        } else {
            this.filename = new String[0];
        }
    }

    protected File getFile() {
        File file = ROOT_FOLD;
        for (int i = 0; i < filename.length - 1; i++) {
            String s = filename[i];
            file = new File(file, s);
            if (!file.exists() && !file.mkdirs()) {
                throw new RuntimeException("文件创建失败: " + file.getAbsolutePath());
            }
        }
        file = new File(file, filename[filename.length - 1]);
        return file;
    }

    protected List<T> loadFromFile() {
        if (this.storeToFileFlag) {
            File file = this.getFile();
            if (file.exists()) {
                try {
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    if (bytes.length > 0) {
                        String content = new String(bytes, StandardCharsets.UTF_8);
                        return json.withSource(content).readList(this.cls);
                    }
                } catch (IOException e) {
                    System.err.println("read file error: " + file.getAbsolutePath());
                }
            }
        }
        return new ArrayList<>();
    }

    protected void storeToFile(Object value) {
        if (!this.storeToFileFlag) return;

        File file = this.getFile();
        if (value == null) {
            if (!file.delete()) {
                System.err.println("文件删除失败: " + file.getAbsolutePath());
            }
            return;
        }
        try {
            if (file.exists()) {
                Files.writeString(file.toPath(), json.parse(value), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            } else {
                Files.writeString(file.toPath(), json.parse(value), StandardOpenOption.CREATE);
            }
        } catch (Exception e) {
            System.err.println("store file error: " + e.getMessage() + ", file: " + file.getAbsolutePath());
        }
    }
}
