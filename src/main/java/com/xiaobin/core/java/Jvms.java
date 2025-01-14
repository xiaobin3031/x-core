package com.xiaobin.core.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * created by xuweibin at 2025/1/6 10:42
 */
public class Jvms {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("empty file");
            return;
        }

        File clsFile = new File(args[0]);
        if (!clsFile.exists()) {
            System.err.println("file not exist");
            return;
        }

        byte[] bytes = Files.readAllBytes(clsFile.toPath());
        ClassFormat classFormat = new ClassFormat(bytes);
        classFormat.resolve();
        System.out.println("=========");
    }
}
