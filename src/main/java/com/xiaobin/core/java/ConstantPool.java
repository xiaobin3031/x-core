package com.xiaobin.core.java;

import com.xiaobin.core.java.model.U2;
import com.xiaobin.core.java.model.U4;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

/**
 * created by xuweibin at 2025/1/2 17:08
 */
@Getter
@Setter
class ConstantPool {

    final static byte Utf8 = 1; // 45.3 1.0.2
    final static byte Integer = 3; // 45.3 1.0.2
    final static byte Float = 4; // 45.3 1.0.2
    final static byte Long = 5; // 45.3 1.0.2
    final static byte Double = 6; // 45.3 1.0.2
    final static byte Class = 7; // 45.3 1.0.2
    final static byte String = 8; // 45.3 1.0.2
    final static byte Fieldref = 9; // 45.3 1.0.2
    final static byte Methodref = 10; // 45.3 1.0.2
    final static byte InterfaceMethodref = 11; // 45.3 1.0.2
    final static byte NameAndType = 12; // 45.3 1.0.2
    final static byte MethodHandle = 15; // 51.0 7
    final static byte MethodType = 16; // 51.0 7
    final static byte Dynamic = 17; // 55.0 11
    final static byte InvokeDynamic = 18; // 51.0 7
    final static byte Module = 19; // 53.0 9
    final static byte Package = 20; // 53.0 9

    int count;

    // length = count - 1
    CpInfo[] cpInfo;

    abstract static class CpInfo {

        byte tag;

        abstract Object getValue(CpInfo[] cpInfos);

        abstract String getValue2(CpInfo[] cpInfos);

        protected CpInfo(byte tag) {
            this.tag = tag;
        }

        static class CpClass extends CpInfo {

            // u2
            int nameIndex;

            CpClass(byte tag, U2 nameIndex) {
                super(tag);
                this.nameIndex = nameIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[nameIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpFieldRef extends CpInfo {
            // u2
            int classIndex;

            // u2
            int nameAndTypeIndex;

            CpFieldRef(byte tag, U2 classIndex, U2 nameAndTypeIndex) {
                super(tag);
                this.classIndex = classIndex.getValue();
                this.nameAndTypeIndex = nameAndTypeIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[classIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return (java.lang.String) cpInfos[nameAndTypeIndex].getValue(cpInfos);
            }
        }

        static class CpMethodRef extends CpInfo {
            // u2
            int classIndex;

            // u2
            int nameAndTypeIndex;

            CpMethodRef(byte tag, U2 classIndex, U2 nameAndTypeIndex) {
                super(tag);
                this.classIndex = classIndex.getValue();
                this.nameAndTypeIndex = nameAndTypeIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[classIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return (java.lang.String) cpInfos[nameAndTypeIndex].getValue(cpInfos);
            }
        }

        static class CpInterfaceMethodRef extends CpInfo {
            // u2
            int classIndex;

            // u2
            int nameAndTypeIndex;

            CpInterfaceMethodRef(byte tag, U2 classIndex, U2 nameAndTypeIndex) {
                super(tag);
                this.classIndex = classIndex.getValue();
                this.nameAndTypeIndex = nameAndTypeIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return (String) cpInfos[classIndex].getValue(cpInfos) + cpInfos[nameAndTypeIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return (java.lang.String) cpInfos[nameAndTypeIndex].getValue(cpInfos);
            }
        }

        static class CpString extends CpInfo {
            // u2
            int stringIndex;

            CpString(byte tag, U2 stringIndex) {
                super(tag);
                this.stringIndex = stringIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[stringIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpInteger extends CpInfo {
            // u4
            int value;

            CpInteger(byte tag, U4 bytes) {
                super(tag);
                this.value = bytes.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return value;
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpFloat extends CpInfo {

            int value;

            CpFloat(byte tag, U4 bytes) {
                super(tag);
                this.value = bytes.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                int bits = value;
                if (bits == 0x7f800000) return java.lang.Float.POSITIVE_INFINITY;
                if (bits == 0xff800000) return java.lang.Float.NEGATIVE_INFINITY;

                if (bits >= 0x7f800001 && bits < 0x7fffffff || bits >= 0xff800001 && bits < 0xfffffff)
                    return java.lang.Float.NaN;

                int s = ((bits >> 31) == 0) ? 1 : -1;
                int e = ((bits >> 23) & 0xff);
                int m = (e == 0) ?
                        (bits & 0x7fffff) << 1 :
                        (bits & 0x7fffff) | 0x800000;
                return (float) (s * m * Math.pow(2.0, e - 150.0));
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpLong extends CpInfo {

            int high;

            int low;

            CpLong(byte tag, U4 highBytes, U4 lowBytes) {
                super(tag);
                this.high = highBytes.getValue();
                this.low = lowBytes.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                long high = this.high;
                high <<= 32;
                return high + this.low;
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpDouble extends CpInfo {

            int high;
            int low;

            CpDouble(byte tag, U4 highBytes, U4 lowBytes) {
                super(tag);
                this.high = highBytes.getValue();
                this.low = lowBytes.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                long bits = this.high;
                bits <<= 32;
                bits += this.low;
                if (bits == 0x7ff0000000000000L) return java.lang.Double.POSITIVE_INFINITY;
                if (bits == 0xfff0000000000000L) return java.lang.Double.NEGATIVE_INFINITY;

                if (bits >= 0x7ff0000000000001L && bits < 0x7fffffffffffffffL || bits >= 0xfff0000000000001L && bits < 0xffffffffffffffffL)
                    return java.lang.Double.NaN;

                int s = ((bits >> 63) == 0) ? 1 : -1;
                int e = (int) ((bits >> 52) & 0x7ffL);
                long m = (e == 0) ?
                        (bits & 0xfffffffffffffL) << 1 :
                        (bits & 0xfffffffffffffL) | 0x10000000000000L;
                return s * m * Math.pow(2.0, e - 1075.0);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpNameAndType extends CpInfo {

            // u2
            int nameIndex;

            // u2
            int descriptorIndex;

            CpNameAndType(byte tag, U2 nameIndex, U2 descriptorIndex) {
                super(tag);
                this.nameIndex = nameIndex.getValue();
                this.descriptorIndex = descriptorIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[nameIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return (java.lang.String) cpInfos[descriptorIndex].getValue(cpInfos);
            }
        }

        static class CpUtf8 extends CpInfo {

            // u2
            int length;

            // length
            byte[] bytes;

            CpUtf8(byte tag, int length, byte[] bytes) {
                super(tag);
                this.length = length;
                this.bytes = bytes;
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return new String(bytes, StandardCharsets.UTF_8);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpMethodHandle extends CpInfo {

            // u1
            byte referenceKind;

            // u2
            int referenceIndex;

            CpMethodHandle(byte tag, byte referenceKind, U2 referenceIndex) {
                super(tag);
                this.referenceKind = referenceKind;
                this.referenceIndex = referenceIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[referenceIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpMethodType extends CpInfo {

            // u2
            int descriptorIndex;

            CpMethodType(byte tag, U2 descriptorIndex) {
                super(tag);
                this.descriptorIndex = descriptorIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[descriptorIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpDynamic extends CpInfo {

            // u2
            int bootstrapMethodAttrIndex;

            // u2
            int nameAndTypeIndex;

            CpDynamic(byte tag, U2 bootstrapMethodAttrIndex, U2 nameAndTypeIndex) {
                super(tag);
                this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex.getValue();
                this.nameAndTypeIndex = nameAndTypeIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return (String) cpInfos[bootstrapMethodAttrIndex].getValue(cpInfos) + cpInfos[nameAndTypeIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return (java.lang.String) cpInfos[nameAndTypeIndex].getValue(cpInfos);
            }
        }

        static class CpInvokeDynamic extends CpInfo {

            // u2
            int bootstrapMethodAttrIndex;

            // u2
            int nameAndTypeIndex;

            CpInvokeDynamic(byte tag, U2 bootstrapMethodAttrIndex, U2 nameAndTypeIndex) {
                super(tag);
                this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex.getValue();
                this.nameAndTypeIndex = nameAndTypeIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return (String) cpInfos[bootstrapMethodAttrIndex].getValue(cpInfos) + cpInfos[nameAndTypeIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return (java.lang.String) cpInfos[nameAndTypeIndex].getValue(cpInfos);
            }
        }

        static class CpModule extends CpInfo {

            // u2
            int nameIndex;

            CpModule(byte tag, U2 nameIndex) {
                super(tag);
                this.nameIndex = nameIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[nameIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        static class CpPackage extends CpInfo {

            // u2
            int nameIndex;

            CpPackage(byte tag, U2 nameIndex) {
                super(tag);
                this.nameIndex = nameIndex.getValue();
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return cpInfos[nameIndex].getValue(cpInfos);
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }
    }
}
