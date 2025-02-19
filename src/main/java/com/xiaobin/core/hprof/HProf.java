package com.xiaobin.core.hprof;

/**
 * created by xuweibin at 2025/2/18
 */
public class HProf {

    private String version;

    private long identifierSize;

    private long timestamp;

    /**
     * {@link HProfTag}
     */
    private int tag;

    /**
     * number of microseconds since the time stamp in the header
     */
    private long time;

    /**
     * number of bytes that follow this u4 field and belong to this record
     */
    private long bodyLength;
}
