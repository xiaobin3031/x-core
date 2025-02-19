package com.xiaobin.core.hprof;

import java.util.List;

/**
 * created by xuweibin at 2025/2/18
 */
class CpuSamples {

    long sampleSize;
    List<TracesEntry> tracesEntries;

    static class TracesEntry {
        long sampleSize;
        long stackTraceSerialNumber;
    }
}
