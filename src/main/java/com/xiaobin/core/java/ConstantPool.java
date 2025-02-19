package com.xiaobin.core.java;

import com.xiaobin.core.model.U2;
import com.xiaobin.core.model.U4;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

/**
 * created by xuweibin at 2025/1/2 17:08
 */
@Getter
@Setter
public class ConstantPool {

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

    public abstract static class CpInfo {

        byte tag;

        abstract Object getValue(CpInfo[] cpInfos);

        abstract String getValue2(CpInfo[] cpInfos);

        protected CpInfo(byte tag) {
            this.tag = tag;
        }

        public static class CpClass extends CpInfo {

            // u2
            int nameIndex;

            @Getter
            String name;

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

        public static class CpFieldRef extends CpInfo {
            // u2
            int classIndex;

            @Getter
            String className;

            // u2
            int nameAndTypeIndex;

            @Getter
            CpInfo.CpNameAndType nameAndType;

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

        public static class CpMethodRef extends CpInfo {
            // u2
            int classIndex;

            @Getter
            String className;

            // u2
            int nameAndTypeIndex;

            @Getter
            CpInfo.CpNameAndType nameAndType;

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

        public static class CpInterfaceMethodRef extends CpInfo {
            // u2
            int classIndex;

            @Getter
            String className;

            // u2
            int nameAndTypeIndex;

            @Getter
            CpInfo.CpNameAndType nameAndType;

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

        public static class CpString extends CpInfo {
            // u2
            int stringIndex;

            @Getter
            String name;

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

        @Getter
        public static class CpInteger extends CpInfo {
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

        public static class CpFloat extends CpInfo {

            int value;

            @Getter
            float floatValue;

            CpFloat(byte tag, U4 bytes) {
                super(tag);
                this.value = bytes.getValue();

                int bits = value;
                if (bits == 0x7f800000) {
                    floatValue = java.lang.Float.POSITIVE_INFINITY;
                    return;
                }
                if (bits == 0xff800000) {
                    floatValue = java.lang.Float.NEGATIVE_INFINITY;
                    return;
                }

                if (bits >= 0x7f800001 && bits < 0x7fffffff || bits >= 0xff800001 && bits < 0xfffffff) {
                    floatValue = java.lang.Float.NaN;
                    return;
                }

                int s = ((bits >> 31) == 0) ? 1 : -1;
                int e = ((bits >> 23) & 0xff);
                int m = (e == 0) ?
                        (bits & 0x7fffff) << 1 :
                        (bits & 0x7fffff) | 0x800000;
                floatValue = (float) (s * m * Math.pow(2.0, e - 150.0));
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return floatValue;
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        public static class CpLong extends CpInfo {

            int high;

            int low;

            @Getter
            long value;

            CpLong(byte tag, U4 highBytes, U4 lowBytes) {
                super(tag);
                this.high = highBytes.getValue();
                this.low = lowBytes.getValue();

                long high = this.high;
                high <<= 32;
                value = high + this.low;
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

        public static class CpDouble extends CpInfo {

            int high;
            int low;

            double doubleValue;

            CpDouble(byte tag, U4 highBytes, U4 lowBytes) {
                super(tag);
                this.high = highBytes.getValue();
                this.low = lowBytes.getValue();

                long bits = this.high;
                bits <<= 32;
                bits += this.low;
                if (bits == 0x7ff0000000000000L) {
                    doubleValue = java.lang.Double.POSITIVE_INFINITY;
                    return;
                }
                if (bits == 0xfff0000000000000L) {
                    doubleValue = java.lang.Double.NEGATIVE_INFINITY;
                    return;
                }

                if (bits >= 0x7ff0000000000001L && bits < 0x7fffffffffffffffL || bits >= 0xfff0000000000001L && bits < 0xffffffffffffffffL) {
                    doubleValue = java.lang.Double.NaN;
                    return;
                }

                int s = ((bits >> 63) == 0) ? 1 : -1;
                int e = (int) ((bits >> 52) & 0x7ffL);
                long m = (e == 0) ?
                        (bits & 0xfffffffffffffL) << 1 :
                        (bits & 0xfffffffffffffL) | 0x10000000000000L;
                doubleValue = s * m * Math.pow(2.0, e - 1075.0);
            }

            @Override
            Object getValue(CpInfo[] cpInfos) {
                return doubleValue;
            }

            @Override
            java.lang.String getValue2(CpInfo[] cpInfos) {
                return null;
            }
        }

        public static class CpNameAndType extends CpInfo {

            // u2
            int nameIndex;

            // u2
            int descriptorIndex;

            @Getter
            String name;

            @Getter
            String descriptor;

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

        public static class CpUtf8 extends CpInfo {

            // u2
            int length;

            // length
            byte[] bytes;

            @Getter
            String value;

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

        public static class CpMethodHandle extends CpInfo {

            // u1
            /**
             * field ref
             * 1 REF_getField
             * 2 REF_getStatic
             * 3 REF_putField
             * 4 REF_putStatic
             *
             * method ref
             * 5 REF_invokeVirtual
             * 8 REF_newInvokeSpecial
             *
             * method ref or interface method ref
             * 6 REF_invokeStatic
             * 7 REF_invokeSpecial
             *
             * interface method ref
             * 9 REF_invokeInterface
             */
            byte referenceKind;

            // u2
            int referenceIndex;

            @Getter
            CpInfo.CpFieldRef fieldRef;

            @Getter
            CpInfo.CpMethodRef methodRef;

            @Getter
            CpInfo.CpInterfaceMethodRef interfaceMethodRef;

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

        public static class CpMethodType extends CpInfo {

            // u2
            int descriptorIndex;

            @Getter
            String descriptor;

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

        public static class CpDynamic extends CpInfo {

            // u2
            int bootstrapMethodAttrIndex;

            // u2
            int nameAndTypeIndex;

            @Getter
            CpInfo.CpNameAndType nameAndType;

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

        public static class CpInvokeDynamic extends CpInfo {

            // u2
            int bootstrapMethodAttrIndex;

            // u2
            int nameAndTypeIndex;

            @Getter
            CpInfo.CpNameAndType nameAndType;

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

        public static class CpModule extends CpInfo {

            // u2
            int nameIndex;

            @Getter
            String name;

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

        public static class CpPackage extends CpInfo {

            // u2
            int nameIndex;

            @Getter
            String name;

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
