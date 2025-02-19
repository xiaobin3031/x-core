package com.xiaobin.core.hprof;

import java.util.List;

/**
 * created by xuweibin at 2025/2/18
 */
class HeapDumpBase {

    int subTag;

    static class RootUnknown {
        long objectId;
    }

    static class RootJNIGlobal {
        long objectId;
        long jniGlobalRefId;
    }

    static class RootJNILocal {
        long objectId;
        long threadSerialNumber;
        long stackFrameDepth;
    }

    static class RootJavaFrame {
        long objectId;
        long threadSerialNumber;
        long stackFrameDepth;
    }

    static class RootNativeStack {
        long objectId;
        long threadSerialNumber;
    }

    static class RootStickyClass {
        long objectId;
    }

    static class RootThreadBlock {
        long objectId;
        long threadSerialNumber;
    }

    static class RootMonitorUsed {
        long objectId;
    }

    static class RootThreadObject {
        long objectId;
        long threadSerialNumber;
        long stackTraceSerialNumber;
    }

    static class ClassDump {
        long classObjectId;
        long stackTraceSerialNumber;
        long superClassObjectId;
        long classLoaderObjectId;
        long signersObjectId;
        long protectionDomainObjectId;
        long reserved1;
        long reserved2;
        long instanceSize;

        int cpSize;
        List<CpEntry> cpEntries;

        int staticFieldSize;
        List<StaticFieldEntry> staticFieldEntries;

        int instanceFieldSize;
        List<InstanceFieldEntry> instanceFieldEntries;
    }

    static class CpEntry{
        int index;

        /**
         * {@link BasicType}
         */
        int type;

        long value;
    }

    static class StaticFieldEntry {
        long nameId;

        /**
         * {@link BasicType}
         */
        int type;

        long value;
    }

    static class InstanceFieldEntry {
        long nameId;

        /**
         * {@link BasicType}
         */
        int type;
    }

    static class InstanceDump {
        long objectId;
        long stackTraceSerialNumber;
        long classId;
        long length;
        long[] values;
    }

    static class ObjectArrayDump {
        long objectId;
        long stackTraceSerialNumber;
        long elementLength;
        long arrayClassId;
        long[] elements;
    }

    static class PrimitiveArrayDump {
        long objectId;
        long stackTraceSerialNumber;
        long elementLength;
        /**
         * {@link BasicType}
         */
        int elementType;
        int[] elements;
    }
}
