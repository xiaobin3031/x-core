package com.xiaobin.core.hprof;

/**
 * created by xuweibin at 2025/2/18
 */
class StackFrame {

    long frameId;

    long methodNameId;

    long methodSignatureId;

    long sourceFileId;

    long serialNumber;

    /**
     * > 0 line number
     * 0 no line information available
     * -1 unknown location
     * -2 compiled method (Not implemented)
     * -3 native method (Not implemented)
     */
    long lineNumber;
}
