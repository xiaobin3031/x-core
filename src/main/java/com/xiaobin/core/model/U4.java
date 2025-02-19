package com.xiaobin.core.model;

/**
 * created by xuweibin at 2025/1/6 17:20
 */
public class U4 {

    private final byte[] bytes;

    public U4(byte[] bytes) {
        assert bytes != null && bytes.length == 4;
        this.bytes = bytes;
    }

    public int getValue() {
        return (bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }
}
