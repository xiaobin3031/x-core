package com.xiaobin.core.model;

/**
 * created by xuweibin at 2025/1/6 17:19
 */
public class U2 {

    private final byte[] bytes;

    public U2(byte[] bytes) {
        assert bytes != null && bytes.length == 2;
        this.bytes = bytes;
    }

    public int getValue() {
        return bytes[0] << 8 | bytes[1] & 0xFF;
    }
}
