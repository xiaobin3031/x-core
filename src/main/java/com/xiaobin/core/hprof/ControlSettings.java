package com.xiaobin.core.hprof;

/**
 * created by xuweibin at 2025/2/18
 */
class ControlSettings {

    /**
     * Bit mask flags:
     * 0x1 alloc traces on/off
     * 0x2 cpu sampling on/off
     */
    long flags;

    int stackTraceDepth;
}
