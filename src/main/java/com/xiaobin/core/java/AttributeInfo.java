package com.xiaobin.core.java;

import com.xiaobin.core.util.ByteReader;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * created by xuweibin at 2025/1/2 17:32
 *
 */
@Getter
public class AttributeInfo {

    int attributeNameIndex;

    String attributeName;

    int attributeLength;

    byte[] infoBytes;

    // attributeLength
    AttrInfo info;

    @Getter
    public abstract static class AttrInfo {

        public static class ConstantValue extends AttrInfo {

            int constantValueIndex;

            Object constantValue;
        }

        public static class Code extends AttrInfo {

            int maxStack;

            int maxLocales;

            int codeLength;

            byte[] code;

            int exceptionTableLength;

            ExceptionTable[] exceptionTable;

            int attributesCount;

            @Getter
            AttributeInfo[] attributes;

            @Getter
            List<OpCodes> opCodesList;

            static class ExceptionTable {
                int startPc;

                int endPc;

                int handlerPc;

                int catchPc;
            }

            // 翻译成指令码
            void instrument() {
                opCodesList = new ArrayList<>();
                if (codeLength > 0) {
                    ByteReader byteReader = new ByteReader(code);
                    OpCodeFillVisitor visitor = new OpCodeFillVisitor(byteReader);
                    while (!byteReader.atEnd()) {
                        int opcode = byteReader.read1() & 0xff;
                        OpCodes opCodes = OpCodes.fromOpCode(opcode);
                        if (opCodes == null) {
                            throw new RuntimeException("invalid op code: " + opcode);
                        }
                        opCodes.accept(visitor);
                        opCodesList.add(opCodes);
                    }
                } else {
                    System.out.println("code length == 0");
                }
            }
        }

        public static class StaticMapTable extends AttrInfo {
            int numberOfEntries;

            // todo 暂时不解析
            byte[] entryBytes;
        }

        public static class Exceptions extends AttrInfo {

            int numberOfExceptions;

            int[] exceptionIndexTable;
        }

        public static class InnerClasses extends AttrInfo {
            int numberOfClasses;

            InnerClass[] innerClasses;

            static class InnerClass {
                int innerClassInfoIndex;

                int outerClassInfoIndex;

                int innerNameIndex;

                int innerClassAccessFlags;
            }
        }

        public static class EnclosingMethod extends AttrInfo {
            int classIndex;

            int methodIndex;
        }

        public static class Synthetic extends AttrInfo {
        }

        public static class Signature extends AttrInfo {
            int signatureIndex;

            String signature;
        }

        public static class SourceFile extends AttrInfo {
            int sourceFileIndex;

            @Getter
            String sourceFile;
        }

        public static class SourceDebugExtension extends AttrInfo {
            byte[] debugExtension;
        }

        public static class LineNumberTable extends AttrInfo {
            int lineNumberTableLength;

            LineNumber[] lineNumberTable;

            static class LineNumber {
                int startPc;

                int lineNumber;
            }
        }

        public static class LocalVariableTable extends AttrInfo {
            int localVariableTableLength;

            @Getter
            LocalVariable[] localVariableTable;

            public static class LocalVariable {
                int startPc;

                int length;

                int nameIndex;

                @Getter
                String name;

                int descriptorIndex;

                @Getter
                String descriptor;

                int index;
            }
        }

        public static class LocalVariableTypeTable extends AttrInfo {
            int localVariableTypeTableLength;

            LocalVariableType[] localVariableTypeTable;

            public static class LocalVariableType {
                int startPc;

                int length;

                int nameIndex;

                @Getter
                String name;

                int signatureIndex;

                @Getter
                String signature;

                int index;
            }
        }

        public static class Deprecated extends AttrInfo {

        }

        public static class RuntimeVisibleAnnotations extends AttrInfo {
            int numAnnotations;
            Annotation[] annotations;
        }

        public static class Annotation {
            int typeIndex;
            int numPairs;
            AnnotationPair[] pairs;
        }

        public static class AnnotationPair {
            int nameIndex;
            ElementValue elementValue;
        }

        public static class ElementValue {
            char tag;

            // tag == B || C || D || F || I || J || S || Z || s
            int constIndex;

            // tag == e
            int typeNameIndex;
            int constNameIndex;

            // tag == c
            int classInfoIndex;

            // tag == @
            Annotation annotation;

            // tag == [
            int numValues;
            ElementValue[] values;
        }

        public static class RuntimeInvisibleAnnotations extends AttrInfo {
            int numAnnotations;
            Annotation[] annotations;
        }

        public static class RuntimeVisibleParameterAnnotations extends AttrInfo {
            int numParameters;
            ParameterAnnotation[] parameterAnnotations;
        }

        public static class ParameterAnnotation {
            int numAnnotations;
            Annotation[] annotations;
        }

        public static class RuntimeInvisibleParameterAnnotations extends AttrInfo {
            int numParameters;
            ParameterAnnotation[] parameterAnnotations;
        }

        public static class RuntimeVisibleTypeAnnotations extends AttrInfo {
            int numAnnotations;
            TypeAnnotation[] annotations;
        }

        public static class TypeAnnotation {
            // todo
        }

        public static class RuntimeInvisibleTypeAnnotations extends AttrInfo {
            int numAnnotations;
            TypeAnnotation[] annotations;
        }

        public static class AnnotationDefault extends AttrInfo {
            ElementValue defaultValue;
        }

        public static class BootstrapMethods extends AttrInfo {

            int numBootstrapMethods;
            BootstrapMethod[] bootstrapMethods;

            static class BootstrapMethod {
                int bootstrapMethodRef;
                int numBootstrapArguments;
                int[] bootstrapArguments;
            }
        }

        public static class MethodParameters extends AttrInfo {
            int parametersCount;
            Parameter[] parameters;

            static class Parameter {
                int nameIndex;
                int accessFlags;
            }

        }

        public static class Module extends AttrInfo {

            int moduleNameIndex;
            int moduleFlags;
            int moduleVersionIndex;

            int requiresCount;
            Require[] requires;

            static class Require {
                int requiresIndex;
                int requiresFlags;
                int requiresVersionIndex;
            }

            int exportsCount;
            Export[] exports;

            static class Export {
                int exportsIndex;
                int exportsFlags;
                int exportsToCount;
                int[] exportsToIndexes;
            }

            int opensCount;
            Open[] opens;

            static class Open {
                int opensIndex;
                int opensFlags;
                int opensToCount;
                int[] opensToIndexes;
            }

            int usesCount;
            int[] usesIndexes;

            int providersCount;
            Provides[] provides;

            static class Provides {
                int providesIndex;
                int providesWithCount;
                int[] providersIndexes;
            }
        }

        public static class ModulePackages extends AttrInfo {
            int packagesCount;
            int[] packagesIndexes;
        }

        public static class ModuleMainClass extends AttrInfo {
            int mainClassIndex;
        }

        public static class NestHost extends AttrInfo {
            int hostClassIndex;
        }

        public static class NestMembers extends AttrInfo {
            int numberOfClasses;
            int[] classes;
        }

        public static class Record extends AttrInfo {
            int componentsCount;
            Component[] components;

            static class Component {
                int nameIndex;
                int descriptorIndex;
                int attributesCount;
                AttributeInfo[] attributes;
            }
        }

        public static class PermittedSubclasses extends AttrInfo {
            int numberOfClasses;
            int[] classes;
        }
    }
}