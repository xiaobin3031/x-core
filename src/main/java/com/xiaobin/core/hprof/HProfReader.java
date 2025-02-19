package com.xiaobin.core.hprof;

import com.xiaobin.core.util.ByteReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * created by xuweibin at 2025/2/18
 */
class HProfReader {

    private final ByteReader hprofByteReader;

    HProfReader(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        hprofByteReader = new ByteReader(bytes);
    }

    void read() {

    }

    private void readHeader(){

    }

}
