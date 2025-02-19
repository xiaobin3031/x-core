package com.xiaobin.core.util;

import com.xiaobin.core.model.U2;
import com.xiaobin.core.model.U4;

import java.util.Arrays;

/**
 * created by xuweibin at 2025/1/7 13:10
 */
public class ByteReader {

    private int offset = 0;
    private final byte[] bytes;

    public ByteReader(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] readBytes(int length) {
        byte[] bytes1 = Arrays.copyOfRange(bytes, offset, offset + length);
        offset += length;
        return bytes1;
    }

    public byte read1() {
        byte b = bytes[offset];
        offset++;
        return b;
    }

    public U2 readU2() {
        return new U2(readBytes(2));
    }

    public U4 readU4() {
        return new U4(readBytes(4));
    }

    public boolean atEnd() {
        return offset >= bytes.length;
    }

    public void padding() {
        while (offset % 4 != 0) {
            offset++;
        }
    }
}
