package com.xiaobin.core.java;

import java.util.ArrayList;
import java.util.List;

/**
 * created by xuweibin at 2025/1/2 17:32
 * todo
 */
class AttributeInfo {

    int attributeNameIndex;

    String attributeName;

    int attributeLength;

    byte[] infoBytes;

    // attributeLength
    AttrInfo info;

    abstract static class AttrInfo {

        static class ConstantValue extends AttrInfo {

            int constantValueIndex;

            Object constantValue;
        }

        static class Code extends AttrInfo {

            int maxStack;

            int maxLocales;

            int codeLength;

            byte[] code;

            int exceptionTableLength;

            ExceptionTable[] exceptionTable;

            int attributesCount;

            AttributeInfo[] attributes;

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

        static class StaticMapTable extends AttrInfo {
            int numberOfEntries;

            // todo 暂时不解析
            byte[] entryBytes;
        }

        static class Exceptions extends AttrInfo {

            int numberOfExceptions;

            int[] exceptionIndexTable;
        }

        static class InnerClasses extends AttrInfo {
            int numberOfClasses;

            InnerClass[] innerClasses;

            static class InnerClass {
                int innerClassInfoIndex;

                int outerClassInfoIndex;

                int innerNameIndex;

                int innerClassAccessFlags;
            }
        }

        static class EnclosingMethod extends AttrInfo {
            int classIndex;

            int methodIndex;
        }

        static class Synthetic extends AttrInfo {
        }

        static class Signature extends AttrInfo {
            int signatureIndex;

            String signature;
        }

        static class SourceFile extends AttrInfo {
            int sourceFileIndex;

            String sourceFile;
        }

        static class SourceDebugExtension extends AttrInfo {
            byte[] debugExtension;
        }

        static class LineNumberTable extends AttrInfo {
            int lineNumberTableLength;

            LineNumber[] lineNumberTable;

            static class LineNumber {
                int startPc;

                int lineNumber;
            }
        }

        static class LocalVariableTable extends AttrInfo {
            int localVariableTableLength;

            LocalVariable[] localVariableTable;

            static class LocalVariable {
                int startPc;

                int length;

                int nameIndex;

                int descriptorIndex;

                int index;
            }
        }

        static class LocalVariableTypeTable extends AttrInfo {
            int localVariableTypeTableLength;

            LocalVariableType[] localVariableTypeTable;

            static class LocalVariableType {
                int startPc;

                int length;

                int nameIndex;

                int signatureIndex;

                int index;
            }
        }

        static class Deprecated extends AttrInfo {

        }

        static class RuntimeVisibleAnnotations extends AttrInfo {
            int numAnnotations;
            Annotation[] annotations;
        }

        static class Annotation {
            int typeIndex;
            int numPairs;
            AnnotationPair[] pairs;
        }

        static class AnnotationPair {
            int nameIndex;
            ElementValue elementValue;
        }

        static class ElementValue {
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

        static class RuntimeInvisibleAnnotations extends AttrInfo {
            int numAnnotations;
            Annotation[] annotations;
        }

        static class RuntimeVisibleParameterAnnotations extends AttrInfo {
            int numParameters;
            ParameterAnnotation[] parameterAnnotations;
        }

        static class ParameterAnnotation {
            int numAnnotations;
            Annotation[] annotations;
        }

        static class RuntimeInvisibleParameterAnnotations extends AttrInfo {
            int numParameters;
            ParameterAnnotation[] parameterAnnotations;
        }

        static class RuntimeVisibleTypeAnnotations extends AttrInfo {
            int numAnnotations;
            TypeAnnotation[] annotations;
        }

        static class TypeAnnotation {
            // todo
        }

        static class RuntimeInvisibleTypeAnnotations extends AttrInfo {
            int numAnnotations;
            TypeAnnotation[] annotations;
        }

        static class AnnotationDefault extends AttrInfo {
            ElementValue defaultValue;
        }

        static class BootstrapMethods extends AttrInfo {

            int numBootstrapMethods;
            BootstrapMethod[] bootstrapMethods;

            static class BootstrapMethod {
                int bootstrapMethodRef;
                int numBootstrapArguments;
                int[] bootstrapArguments;
            }
        }

        static class MethodParameters extends AttrInfo {
            int parametersCount;
            Parameter[] parameters;

            static class Parameter {
                int nameIndex;
                int accessFlags;
            }

        }

        static class Module extends AttrInfo {

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

        static class ModulePackages extends AttrInfo {
            int packagesCount;
            int[] packagesIndexes;
        }

        static class ModuleMainClass extends AttrInfo {
            int mainClassIndex;
        }

        static class NestHost extends AttrInfo {
            int hostClassIndex;
        }

        static class NestMembers extends AttrInfo {
            int numberOfClasses;
            int[] classes;
        }

        static class Record extends AttrInfo {
            int componentsCount;
            Component[] components;

            static class Component {
                int nameIndex;
                int descriptorIndex;
                int attributesCount;
                AttributeInfo[] attributes;
            }
        }

        static class PermittedSubclasses extends AttrInfo {
            int numberOfClasses;
            int[] classes;
        }
    }
}