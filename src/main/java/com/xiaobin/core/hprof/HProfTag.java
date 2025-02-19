package com.xiaobin.core.hprof;

/**
 * created by xuweibin at 2025/2/18
 */
public class HProfTag {

    final int STRING = 0x01;

    final int LOAD_CLASS = 0x02;

    final int UNLOAD_CLASS = 0x03;

    final int STACK_FRAME = 0x04;

    final int STACK_TRACE = 0x05;

    final int ALLOC_SITES = 0x06;

    final int HEAP_SUMMARY = 0x07;

    final int START_THREAD = 0x08;

    final int END_THREAD = 0x09;

    final int HEAP_DUMP = 0x0C;

    final int HEAP_DUMP_SEGMENT = 0x1C;

    final int HEAP_DUMP_END = 0x2C;

    final int CPU_SAMPLES = 0x0D;

    final int CONTROL_SETTINGS = 0x0E;

}
