package com.xiaobin.core.hprof;

/**
 * created by xuweibin at 2025/2/18
 */
class AllocSites {

    /**
     * Bit mask flags:
     * 0x1 incremental vs. complete
     * 0x2 sorted by allocation vs. line
     * 0x4 whether to force GC (Not Implemented)
     */
    int flags;

    /**
     * cutoff ratio (floating point)
     */
    double cutoffRadio;

    long totalLiveBytes;

    long totalLiveInstances;

    long totalBytesAllocated;

    long totalInstanceAllocated;

    long numberOfSites;

    /**
     * array indicator: 0 means not an array, non-zero means an array of this type (See Basic Type)
     * {@link BasicType}
     */
    int arrayIndicator;

    long classSerialNumber;

    long stackTraceSerialNumber;

    long liveBytes;

    long liveInstances;

    long bytesAllocated;

    long instancesAllocated;
}
