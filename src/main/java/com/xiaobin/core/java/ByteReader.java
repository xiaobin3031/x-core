package com.xiaobin.core.java;

import com.xiaobin.core.java.model.U2;
import com.xiaobin.core.java.model.U4;

import java.util.Arrays;

/**
 * created by xuweibin at 2025/1/7 13:10
 */
class ByteReader {

    private int offset = 0;
    private final byte[] bytes;

    ByteReader(byte[] bytes) {
        this.bytes = bytes;
    }

    byte[] readBytes(int length) {
        byte[] bytes1 = Arrays.copyOfRange(bytes, offset, offset + length);
        offset += length;
        return bytes1;
    }

    byte read1() {
        byte b = bytes[offset];
        offset++;
        return b;
    }

    U2 readU2() {
        return new U2(readBytes(2));
    }

    U4 readU4() {
        return new U4(readBytes(4));
    }

    boolean atEnd() {
        return offset >= bytes.length;
    }

    void padding() {
        while (offset % 4 != 0) {
            offset++;
        }
    }
}
