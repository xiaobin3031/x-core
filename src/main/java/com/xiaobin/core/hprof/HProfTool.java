package com.xiaobin.core.hprof;

import java.io.File;

/**
 * created by xuweibin at 2025/2/18
 * <a href="https://hg.openjdk.org/jdk6/jdk6/jdk/raw-file/tip/src/share/demo/jvmti/hprof/manual.html">hprof</a>
 */
public class HProfTool {

    public static void main(String[] args) {
        File file = new File("E:\\log\\dumpfile.hprof");
        if(!file.exists()){
            System.exit(-1);
            return;
        }


    }
}
