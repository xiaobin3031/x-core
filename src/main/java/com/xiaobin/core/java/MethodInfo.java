package com.xiaobin.core.java;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by xuweibin at 2025/1/6 13:36
 */
@Getter
public class MethodInfo {

    private static final int ACC_PUBLIC = 0x0001; // Declared public; may be accessed from outside its package.
    private static final int ACC_PRIVATE = 0x0002; // Declared private; accessible only within thedefining class and other classes belonging to the same nest (§5.4.4).
    private static final int ACC_PROTECTED = 0x0004; // Declared protected; may be accessed withinsubclasses.
    private static final int ACC_STATIC = 0x0008; // Declared static.
    private static final int ACC_FINAL = 0x0010; // Declared final; must not be overridden (§5.4.5).
    private static final int ACC_SYNCHRONIZED = 0x0020; // Declared synchronized; invocation is wrapped by a monitor use.
    private static final int ACC_BRIDGE = 0x0040; // A bridge method, generated by the compiler.
    private static final int ACC_VARARGS = 0x0080; // Declared with variable number of arguments.
    private static final int ACC_NATIVE = 0x0100; // Declared native; implemented in a language other than the Java programming language.
    private static final int ACC_ABSTRACT = 0x0400; // Declared abstract; no implementation is provided.
    private static final int ACC_STRICT = 0x0800; // In a class file whose major version number is at least 46 and at most 60: Declared strictfp.
    private static final int ACC_SYNTHETIC = 0x1000; // Declared synthetic; not present in the source code.

    private static final Map<Integer, String> ACC_FLAGS = new HashMap<>();

    static {
        ACC_FLAGS.put(ACC_PUBLIC, "public");
        ACC_FLAGS.put(ACC_PRIVATE, "private");
        ACC_FLAGS.put(ACC_PROTECTED, "protected");
        ACC_FLAGS.put(ACC_STATIC, "static");
        ACC_FLAGS.put(ACC_FINAL, "final");
        ACC_FLAGS.put(ACC_SYNCHRONIZED, "synchronized");
        ACC_FLAGS.put(ACC_BRIDGE, "bridge");
        ACC_FLAGS.put(ACC_VARARGS, "varargs");
        ACC_FLAGS.put(ACC_NATIVE, "native");
        ACC_FLAGS.put(ACC_ABSTRACT, "abstract");
        ACC_FLAGS.put(ACC_STRICT, "strict");
        ACC_FLAGS.put(ACC_SYNTHETIC, "synthetic");
    }

    int accessFlags;

    int nameIndex;

    String name;

    int descriptorIndex;

    String descriptor;

    int attributesCount;

    AttributeInfo[] attributes;

    String accessFlag() {
        List<String> flags = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : ACC_FLAGS.entrySet()) {
            if ((entry.getKey() & accessFlags) == accessFlags) {
                flags.add(entry.getValue());
            }
        }
        return String.join(",", flags);
    }
}
