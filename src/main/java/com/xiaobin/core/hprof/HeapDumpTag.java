package com.xiaobin.core.hprof;

/**
 * created by xuweibin at 2025/2/18
 */
class HeapDumpTag {

    final static int ROOT_UNKNOWN = 0xff;

    final static int ROOT_JNI_GLOBAL = 0x01;

    final static int ROOT_JNI_LOCAL = 0x02;

    final static int ROOT_JAVA_FRAME = 0x03;

    final static int ROOT_NATIVE_STACK = 0x04;

    final static int ROOT_STICKY_CLASS = 0x05;

    final static int ROOT_THREAD_BLOCK = 0x06;

    final static int ROOT_MONITOR_USED = 0x07;

    final static int ROOT_THREAD_OBJECT = 0x08;

    final static int ROOT_CLASS_DUMP = 0x20;

    final static int ROOT_INSTANCE_DUMP = 0x21;

    final static int OBJECT_ARRAY_DUMP = 0x22;

    final static int PRIM_ARRAY_DUMP = 0x23;
}
