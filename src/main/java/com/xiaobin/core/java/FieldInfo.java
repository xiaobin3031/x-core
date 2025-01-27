package com.xiaobin.core.java;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by xuweibin at 2025/1/2 17:31
 */
public class FieldInfo {

    private final static int ACC_PUBLIC = 0x0001; // Declared public; may be accessed from outside its package.
    private final static int ACC_PRIVATE = 0x0002; // Declared private; accessible only within thedefining class and other classes belonging to the same nest (§5.4.4).
    private final static int ACC_PROTECTED = 0x0004; // Declared protected; may be accessed withinsubclasses.
    private final static int ACC_STATIC = 0x0008; // Declared static.
    private final static int ACC_FINAL = 0x0010; // Declared final; never directly assigned to after object construction (JLS §17.5).
    private final static int ACC_VOLATILE = 0x0040; // Declared volatile; cannot be cached.
    private final static int ACC_TRANSIENT = 0x0080; // Declared transient; not written or read by apersistent object manager.
    private final static int ACC_SYNTHETIC = 0x1000; // Declared synthetic; not present in the source code.
    private final static int ACC_ENUM = 0x4000; // Declared as an element of an enum class.

    private final static Map<Integer, String> ACC_FLAGS = Map.of(
            ACC_PUBLIC, "public",
            ACC_PRIVATE, "private",
            ACC_PROTECTED, "protected",
            ACC_STATIC, "static",
            ACC_FINAL, "final",
            ACC_VOLATILE, "volatile",
            ACC_TRANSIENT, "transient",
            ACC_SYNTHETIC, "synthetic",
            ACC_ENUM, "enum"
    );

    int accessFlags;

    int nameIndex;
    @Getter
    String name;

    int descriptorIndex;
    @Getter
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

    String getName(ConstantPool.CpInfo[] cpInfos) {
        return (String) cpInfos[nameIndex].getValue(cpInfos);
    }

    String getDescriptor(ConstantPool.CpInfo[] cpInfos) {
        return (String) cpInfos[descriptorIndex].getValue(cpInfos);
    }
}
