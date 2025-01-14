package com.xiaobin.core.java;

import com.xiaobin.core.java.model.U2;
import com.xiaobin.core.java.model.U4;

import static com.xiaobin.core.java.ConstantPool.*;

/**
 * created by xuweibin at 2025/1/2 16:51
 */
class ClassFormat {

    String magic;

    String version;

    String javaVersion;

    ConstantPool constantPool;

    int accessFlags;

    int thisClass;

    int superClass;

    int interfacesCount;

    byte[] interfaces;

    FieldInfo[] fields;

    MethodInfo[] methods;

    int attributesCount;

    AttributeInfo[] attributes;

    private final ByteReader byteReader;

    ClassFormat(byte[] bytes) {
        this.byteReader = new ByteReader(bytes);
    }

    void resolve() {
        magic();
        version();
        constantPool();
        accessFlags();
        thisClass();
        superClass();
        interfaces();
        fields();
        methods();
        attributes();
        System.out.println("=====");
    }

    private byte[] readBytes(int length) {
        return this.byteReader.readBytes(length);
    }

    private U2 readU2() {
        return this.byteReader.readU2();
    }

    private U4 readU4() {
        return this.byteReader.readU4();
    }

    private void magic() {
        byte[] magics = readBytes(4);
        StringBuilder magic = new StringBuilder();
        for (byte b : magics) {
            magic.append(java.lang.Integer.toString(b & 0xff, 16));
        }
        this.magic = magic.toString();
    }

    private void version() {
        int m = readU2().getValue();
        int M = readU2().getValue();
        this.version = M + "." + m;
        this.javaVersion = java.lang.String.valueOf(M - 44);
    }

    private void constantPool() {
        ConstantPool constantPool = new ConstantPool();
        int cpCount = readU2().getValue();
        if (cpCount > 0) {
            constantPool.count = cpCount;
            constantPool.cpInfo = new CpInfo[cpCount];
            for (int i = 1; i < cpCount; i++) {
                byte tag = readBytes(1)[0];
                CpInfo cpInfo =
                        switch (tag) {
                            case Class -> new CpInfo.CpClass(tag, readU2());
                            case String -> new CpInfo.CpString(tag, readU2());
                            case MethodType -> new CpInfo.CpMethodType(tag, readU2());
                            case Module -> new CpInfo.CpModule(tag, readU2());
                            case Package -> new CpInfo.CpPackage(tag, readU2());
                            case Fieldref -> new CpInfo.CpFieldRef(tag, readU2(), readU2());
                            case Methodref -> new CpInfo.CpMethodRef(tag, readU2(), readU2());
                            case InterfaceMethodref -> new CpInfo.CpInterfaceMethodRef(tag, readU2(), readU2());
                            case Integer -> new CpInfo.CpInteger(tag, readU4());
                            case Float -> new CpInfo.CpFloat(tag, readU4());
                            case NameAndType -> new CpInfo.CpNameAndType(tag, readU2(), readU2());
                            case Dynamic -> new CpInfo.CpDynamic(tag, readU2(), readU2());
                            case InvokeDynamic -> new CpInfo.CpInvokeDynamic(tag, readU2(), readU2());
                            case Long -> new CpInfo.CpLong(tag, readU4(), readU4());
                            case Double -> new CpInfo.CpDouble(tag, readU4(), readU4());
                            case Utf8 -> {
                                int len = readU2().getValue();
                                yield new CpInfo.CpUtf8(tag, len, readBytes(len));
                            }
                            case MethodHandle -> new CpInfo.CpMethodHandle(tag, readBytes(1)[0], readU2());
                            default -> null;
                        };
                constantPool.cpInfo[i] = cpInfo;
            }
        }

        this.constantPool = constantPool;
    }

    private void accessFlags() {
        this.accessFlags = readU2().getValue();
    }

    private void thisClass() {
        this.thisClass = readU2().getValue();
    }

    private void superClass() {
        this.superClass = readU2().getValue();
    }

    private void interfaces() {
        this.interfacesCount = readU2().getValue();
        this.interfaces = readBytes(interfacesCount * 2);
    }

    private void fields() {
        int fieldsCount = readU2().getValue();
        this.fields = new FieldInfo[fieldsCount];
        if (fieldsCount > 0) {
            for (int i = 0; i < fieldsCount; i++) {
                FieldInfo field = new FieldInfo();
                field.accessFlags = readU2().getValue();
                field.nameIndex = readU2().getValue();
                field.name = getUtf8(field.nameIndex);
                field.descriptorIndex = readU2().getValue();
                field.descriptor = getUtf8(field.descriptorIndex);
                int attrsCount = readU2().getValue();
                field.attributesCount = attrsCount;
                field.attributes = resolveAttributes(attrsCount);
                this.fields[i] = field;
            }
        }
    }

    private AttributeInfo[] resolveAttributes(int attrsCount) {
        AttributeInfo[] infos = new AttributeInfo[attrsCount];
        for (int i = 0; i < attrsCount; i++) {
            AttributeInfo info = new AttributeInfo();
            info.attributeNameIndex = readU2().getValue();
            info.attributeName = getUtf8(info.attributeNameIndex);
            info.attributeLength = readU4().getValue();
            if (info.attributeLength > 0) {
                info.info = switch (info.attributeName) {
                    case "ConstantValue" -> {
                        AttributeInfo.AttrInfo.ConstantValue tmp = new AttributeInfo.AttrInfo.ConstantValue();
                        tmp.constantValueIndex = readU2().getValue();
                        tmp.constantValue = constantPool.cpInfo[tmp.constantValueIndex].getValue(constantPool.cpInfo);
                        yield tmp;
                    }
                    case "Code" -> {
                        AttributeInfo.AttrInfo.Code tmp = new AttributeInfo.AttrInfo.Code();
                        tmp.maxStack = readU2().getValue();
                        tmp.maxLocales = readU2().getValue();
                        int codeLength = readU4().getValue();
                        tmp.codeLength = codeLength;
                        tmp.code = readBytes(codeLength);
                        int exceptionTableLength = readU2().getValue();
                        tmp.exceptionTableLength = exceptionTableLength;
                        tmp.exceptionTable = new AttributeInfo.AttrInfo.Code.ExceptionTable[exceptionTableLength];
                        if (exceptionTableLength > 0) {
                            for (int i1 = 0; i1 < exceptionTableLength; i1++) {
                                AttributeInfo.AttrInfo.Code.ExceptionTable exceptionTable = new AttributeInfo.AttrInfo.Code.ExceptionTable();
                                exceptionTable.startPc = readU2().getValue();
                                exceptionTable.endPc = readU2().getValue();
                                exceptionTable.handlerPc = readU2().getValue();
                                exceptionTable.catchPc = readU2().getValue();
                                tmp.exceptionTable[i1] = exceptionTable;
                            }
                        }
                        tmp.attributesCount = readU2().getValue();
                        tmp.attributes = resolveAttributes(tmp.attributesCount);
                        tmp.instrument();
                        yield tmp;
                    }
                    case "Exceptions" -> {
                        AttributeInfo.AttrInfo.Exceptions tmp = new AttributeInfo.AttrInfo.Exceptions();
                        tmp.numberOfExceptions = readU2().getValue();
                        tmp.exceptionIndexTable = new int[tmp.numberOfExceptions];
                        if (tmp.numberOfExceptions > 0) {
                            for (int i1 = 0; i1 < tmp.numberOfExceptions; i1++) {
                                tmp.exceptionIndexTable[i1] = readU2().getValue();
                            }
                        }
                        yield tmp;
                    }
                    case "InnerClasses" -> {
                        AttributeInfo.AttrInfo.InnerClasses tmp = new AttributeInfo.AttrInfo.InnerClasses();
                        tmp.numberOfClasses = readU2().getValue();
                        tmp.innerClasses = new AttributeInfo.AttrInfo.InnerClasses.InnerClass[tmp.numberOfClasses];
                        if (tmp.numberOfClasses > 0) {
                            for (int i1 = 0; i1 < tmp.numberOfClasses; i1++) {
                                AttributeInfo.AttrInfo.InnerClasses.InnerClass innerClass = new AttributeInfo.AttrInfo.InnerClasses.InnerClass();
                                innerClass.innerClassInfoIndex = readU2().getValue();
                                innerClass.outerClassInfoIndex = readU2().getValue();
                                innerClass.innerNameIndex = readU2().getValue();
                                innerClass.innerClassAccessFlags = readU2().getValue();
                                tmp.innerClasses[i1] = innerClass;
                            }
                        }
                        yield tmp;
                    }
                    case "EnclosingMethod" -> {
                        AttributeInfo.AttrInfo.EnclosingMethod tmp = new AttributeInfo.AttrInfo.EnclosingMethod();
                        tmp.classIndex = readU2().getValue();
                        tmp.methodIndex = readU2().getValue();
                        yield tmp;
                    }
                    case "Synthetic" -> new AttributeInfo.AttrInfo.Synthetic();
                    case "Signature" -> {
                        AttributeInfo.AttrInfo.Signature tmp = new AttributeInfo.AttrInfo.Signature();
                        tmp.signatureIndex = readU2().getValue();
                        tmp.signature = getUtf8(tmp.signatureIndex);
                        yield tmp;
                    }
                    case "SourceFile" -> {
                        AttributeInfo.AttrInfo.SourceFile tmp = new AttributeInfo.AttrInfo.SourceFile();
                        tmp.sourceFileIndex = readU2().getValue();
                        tmp.sourceFile = getUtf8(tmp.sourceFileIndex);
                        yield tmp;
                    }
                    case "SourceDebugExtension" -> {
                        AttributeInfo.AttrInfo.SourceDebugExtension tmp = new AttributeInfo.AttrInfo.SourceDebugExtension();
                        tmp.debugExtension = readBytes(info.attributeLength);
                        yield tmp;
                    }
                    case "LineNumberTable" -> {
                        AttributeInfo.AttrInfo.LineNumberTable tmp = new AttributeInfo.AttrInfo.LineNumberTable();
                        tmp.lineNumberTableLength = readU2().getValue();
                        tmp.lineNumberTable = new AttributeInfo.AttrInfo.LineNumberTable.LineNumber[tmp.lineNumberTableLength];
                        if (tmp.lineNumberTableLength > 0) {
                            for (int i1 = 0; i1 < tmp.lineNumberTableLength; i1++) {
                                AttributeInfo.AttrInfo.LineNumberTable.LineNumber lineNumber = new AttributeInfo.AttrInfo.LineNumberTable.LineNumber();
                                lineNumber.startPc = readU2().getValue();
                                lineNumber.lineNumber = readU2().getValue();
                                tmp.lineNumberTable[i1] = lineNumber;
                            }
                        }
                        yield tmp;
                    }
                    case "LocalVariableTable" -> {
                        AttributeInfo.AttrInfo.LocalVariableTable tmp = new AttributeInfo.AttrInfo.LocalVariableTable();
                        tmp.localVariableTableLength = readU2().getValue();
                        tmp.localVariableTable = new AttributeInfo.AttrInfo.LocalVariableTable.LocalVariable[tmp.localVariableTableLength];
                        if (tmp.localVariableTableLength > 0) {
                            for (int i1 = 0; i1 < tmp.localVariableTableLength; i1++) {
                                AttributeInfo.AttrInfo.LocalVariableTable.LocalVariable localVariable = new AttributeInfo.AttrInfo.LocalVariableTable.LocalVariable();
                                localVariable.startPc = readU2().getValue();
                                localVariable.length = readU2().getValue();
                                localVariable.nameIndex = readU2().getValue();
                                localVariable.descriptorIndex = readU2().getValue();
                                localVariable.index = readU2().getValue();
                                tmp.localVariableTable[i1] = localVariable;
                            }
                        }
                        yield tmp;
                    }
                    case "LocalVariableTypeTable" -> {
                        AttributeInfo.AttrInfo.LocalVariableTypeTable tmp = new AttributeInfo.AttrInfo.LocalVariableTypeTable();
                        tmp.localVariableTypeTableLength = readU2().getValue();
                        tmp.localVariableTypeTable = new AttributeInfo.AttrInfo.LocalVariableTypeTable.LocalVariableType[tmp.localVariableTypeTableLength];
                        if (tmp.localVariableTypeTableLength > 0) {
                            for (int i1 = 0; i1 < tmp.localVariableTypeTableLength; i1++) {
                                AttributeInfo.AttrInfo.LocalVariableTypeTable.LocalVariableType localVariableType = new AttributeInfo.AttrInfo.LocalVariableTypeTable.LocalVariableType();
                                localVariableType.startPc = readU2().getValue();
                                localVariableType.length = readU2().getValue();
                                localVariableType.nameIndex = readU2().getValue();
                                localVariableType.signatureIndex = readU2().getValue();
                                localVariableType.index = readU2().getValue();
                                tmp.localVariableTypeTable[i1] = localVariableType;
                            }
                        }
                        yield tmp;
                    }
                    case "Deprecated" -> new AttributeInfo.AttrInfo.Deprecated();
                    case "RuntimeVisibleAnnotations" -> {
                        AttributeInfo.AttrInfo.RuntimeVisibleAnnotations tmp = new AttributeInfo.AttrInfo.RuntimeVisibleAnnotations();
                        tmp.numAnnotations = readU2().getValue();
                        tmp.annotations = readAnnotations(tmp.numAnnotations);
                        yield tmp;
                    }
                    case "RuntimeInvisibleAnnotations" -> {
                        AttributeInfo.AttrInfo.RuntimeInvisibleAnnotations tmp = new AttributeInfo.AttrInfo.RuntimeInvisibleAnnotations();
                        tmp.numAnnotations = readU2().getValue();
                        tmp.annotations = readAnnotations(tmp.numAnnotations);
                        yield tmp;
                    }
                    case "RuntimeVisibleParameterAnnotations" -> {
                        AttributeInfo.AttrInfo.RuntimeVisibleParameterAnnotations tmp = new AttributeInfo.AttrInfo.RuntimeVisibleParameterAnnotations();
                        tmp.numParameters = readBytes(1)[0];
                        tmp.parameterAnnotations = readParameterAnnotation(tmp.numParameters);
                        yield tmp;
                    }
                    case "RuntimeInvisibleParameterAnnotations" -> {
                        AttributeInfo.AttrInfo.RuntimeInvisibleParameterAnnotations tmp = new AttributeInfo.AttrInfo.RuntimeInvisibleParameterAnnotations();
                        tmp.numParameters = readBytes(1)[0];
                        tmp.parameterAnnotations = readParameterAnnotation(tmp.numParameters);
                        yield tmp;
                    }
                    case "AnnotationDefault" -> {
                        AttributeInfo.AttrInfo.AnnotationDefault tmp = new AttributeInfo.AttrInfo.AnnotationDefault();
                        tmp.defaultValue = readElements(1)[0];
                        yield tmp;
                    }
                    case "BootstrapMethods" -> {
                        AttributeInfo.AttrInfo.BootstrapMethods tmp = new AttributeInfo.AttrInfo.BootstrapMethods();
                        tmp.numBootstrapMethods = readU2().getValue();
                        tmp.bootstrapMethods = new AttributeInfo.AttrInfo.BootstrapMethods.BootstrapMethod[tmp.numBootstrapMethods];
                        if (tmp.numBootstrapMethods > 0) {
                            for (int i1 = 0; i1 < tmp.numBootstrapMethods; i1++) {
                                AttributeInfo.AttrInfo.BootstrapMethods.BootstrapMethod bootstrapMethod = new AttributeInfo.AttrInfo.BootstrapMethods.BootstrapMethod();
                                bootstrapMethod.bootstrapMethodRef = readU2().getValue();
                                bootstrapMethod.numBootstrapArguments = readU2().getValue();
                                bootstrapMethod.bootstrapArguments = new int[bootstrapMethod.numBootstrapArguments];
                                for (int i2 = 0; i2 < bootstrapMethod.numBootstrapArguments; i2++) {
                                    bootstrapMethod.bootstrapArguments[i2] = readU2().getValue();
                                }
                                tmp.bootstrapMethods[i1] = bootstrapMethod;
                            }
                        }
                        yield tmp;
                    }
                    case "MethodParameters" -> {
                        AttributeInfo.AttrInfo.MethodParameters tmp = new AttributeInfo.AttrInfo.MethodParameters();
                        tmp.parametersCount = readBytes(1)[0];
                        tmp.parameters = new AttributeInfo.AttrInfo.MethodParameters.Parameter[tmp.parametersCount];
                        for (int i1 = 0; i1 < tmp.parameters.length; i1++) {
                            AttributeInfo.AttrInfo.MethodParameters.Parameter parameter = new AttributeInfo.AttrInfo.MethodParameters.Parameter();
                            parameter.accessFlags = readBytes(1)[0];
                            parameter.nameIndex = readU2().getValue();
                            tmp.parameters[i1] = parameter;
                        }
                        yield tmp;
                    }
                    case "Module" -> {
                        AttributeInfo.AttrInfo.Module tmp = new AttributeInfo.AttrInfo.Module();
                        tmp.moduleNameIndex = readU2().getValue();
                        tmp.moduleFlags = readU2().getValue();
                        tmp.moduleVersionIndex = readU2().getValue();

                        tmp.requiresCount = readU2().getValue();
                        tmp.requires = new AttributeInfo.AttrInfo.Module.Require[tmp.requiresCount];
                        for (int i1 = 0; i1 < tmp.requiresCount; i1++) {
                            AttributeInfo.AttrInfo.Module.Require require = new AttributeInfo.AttrInfo.Module.Require();
                            require.requiresIndex = readU2().getValue();
                            require.requiresFlags = readU2().getValue();
                            require.requiresVersionIndex = readU2().getValue();
                            tmp.requires[i1] = require;
                        }

                        tmp.exportsCount = readU2().getValue();
                        tmp.exports = new AttributeInfo.AttrInfo.Module.Export[tmp.exportsCount];
                        for (int i1 = 0; i1 < tmp.exportsCount; i1++) {
                            AttributeInfo.AttrInfo.Module.Export export = new AttributeInfo.AttrInfo.Module.Export();
                            export.exportsIndex = readU2().getValue();
                            export.exportsFlags = readU2().getValue();
                            export.exportsToCount = readU2().getValue();
                            export.exportsToIndexes = new int[export.exportsToCount];
                            for (int i2 = 0; i2 < export.exportsToCount; i2++) {
                                export.exportsToIndexes[i2] = readU2().getValue();
                            }
                            tmp.exports[i1] = export;
                        }

                        tmp.opensCount = readU2().getValue();
                        tmp.opens = new AttributeInfo.AttrInfo.Module.Open[tmp.opensCount];
                        for (int i1 = 0; i1 < tmp.opensCount; i1++) {
                            AttributeInfo.AttrInfo.Module.Open open = new AttributeInfo.AttrInfo.Module.Open();
                            open.opensIndex = readU2().getValue();
                            open.opensFlags = readU2().getValue();
                            open.opensToCount = readU2().getValue();
                            open.opensToIndexes = new int[open.opensToCount];
                            for (int i2 = 0; i2 < open.opensToCount; i2++) {
                                open.opensToIndexes[i2] = readU2().getValue();
                            }
                            tmp.opens[i1] = open;
                        }

                        tmp.usesCount = readU2().getValue();
                        tmp.usesIndexes = new int[tmp.usesCount];
                        for (int i1 = 0; i1 < tmp.usesCount; i1++) {
                            tmp.usesIndexes[i1] = readU2().getValue();
                        }

                        tmp.providersCount = readU2().getValue();
                        tmp.provides = new AttributeInfo.AttrInfo.Module.Provides[tmp.providersCount];
                        for (int i1 = 0; i1 < tmp.providersCount; i1++) {
                            AttributeInfo.AttrInfo.Module.Provides provides = new AttributeInfo.AttrInfo.Module.Provides();
                            provides.providesIndex = readU2().getValue();
                            provides.providesWithCount = readU2().getValue();
                            provides.providersIndexes = new int[provides.providesWithCount];
                            for (int i2 = 0; i2 < provides.providesWithCount; i2++) {
                                provides.providersIndexes[i2] = readU2().getValue();
                            }
                            tmp.provides[i1] = provides;
                        }

                        yield tmp;
                    }
                    case "ModulePackages" -> {
                        AttributeInfo.AttrInfo.ModulePackages tmp = new AttributeInfo.AttrInfo.ModulePackages();
                        tmp.packagesCount = readU2().getValue();
                        tmp.packagesIndexes = new int[tmp.packagesCount];
                        for (int i1 = 0; i1 < tmp.packagesCount; i1++) {
                            tmp.packagesIndexes[i1] = readU2().getValue();
                        }
                        yield tmp;
                    }
                    case "ModuleMainClass" -> {
                        AttributeInfo.AttrInfo.ModuleMainClass tmp = new AttributeInfo.AttrInfo.ModuleMainClass();
                        tmp.mainClassIndex = readU2().getValue();
                        yield tmp;
                    }
                    case "NestHost" -> {
                        AttributeInfo.AttrInfo.NestHost tmp = new AttributeInfo.AttrInfo.NestHost();
                        tmp.hostClassIndex = readU2().getValue();
                        yield tmp;
                    }
                    case "NestMembers" -> {
                        AttributeInfo.AttrInfo.NestMembers tmp = new AttributeInfo.AttrInfo.NestMembers();
                        tmp.numberOfClasses = readU2().getValue();
                        tmp.classes = new int[tmp.numberOfClasses];
                        for (int i1 = 0; i1 < tmp.numberOfClasses; i1++) {
                            tmp.classes[i1] = readU2().getValue();
                        }
                        yield tmp;
                    }
                    case "Record" -> {
                        AttributeInfo.AttrInfo.Record tmp = new AttributeInfo.AttrInfo.Record();
                        tmp.componentsCount = readU2().getValue();
                        tmp.components = new AttributeInfo.AttrInfo.Record.Component[tmp.componentsCount];
                        for (int i1 = 0; i1 < tmp.componentsCount; i1++) {
                            AttributeInfo.AttrInfo.Record.Component component = new AttributeInfo.AttrInfo.Record.Component();
                            component.nameIndex = readU2().getValue();
                            component.descriptorIndex = readU2().getValue();
                            component.attributesCount = readU2().getValue();
                            component.attributes = resolveAttributes(component.attributesCount);
                            tmp.components[i1] = component;
                        }
                        yield tmp;
                    }
                    case "PermittedSubclasses" -> {
                        AttributeInfo.AttrInfo.PermittedSubclasses tmp = new AttributeInfo.AttrInfo.PermittedSubclasses();
                        tmp.numberOfClasses = readU2().getValue();
                        tmp.classes = new int[tmp.numberOfClasses];
                        for (int i1 = 0; i1 < tmp.numberOfClasses; i1++) {
                            tmp.classes[i1] = readU2().getValue();
                        }
                        yield tmp;
                    }
                    // todo RuntimeVisibleTypeAnnotations
                    // todo RuntimeInvisibleTypeAnnotations
                    // todo StackMapTable
                    default -> {
                        info.infoBytes = readBytes(info.attributeLength);
                        yield null;
                    }
                };
            }
            infos[i] = info;
        }
        return infos;
    }

    private AttributeInfo.AttrInfo.ParameterAnnotation[] readParameterAnnotation(int numAnnotations) {
        AttributeInfo.AttrInfo.ParameterAnnotation[] annotations = new AttributeInfo.AttrInfo.ParameterAnnotation[numAnnotations];
        for (int i = 0; i < numAnnotations; i++) {
            AttributeInfo.AttrInfo.ParameterAnnotation parameterAnnotation = new AttributeInfo.AttrInfo.ParameterAnnotation();
            parameterAnnotation.numAnnotations = readU2().getValue();
            parameterAnnotation.annotations = readAnnotations(parameterAnnotation.numAnnotations);
            annotations[i] = parameterAnnotation;
        }
        return annotations;
    }

    private AttributeInfo.AttrInfo.Annotation[] readAnnotations(int numAnnotations) {
        AttributeInfo.AttrInfo.Annotation[] annotations = new AttributeInfo.AttrInfo.Annotation[numAnnotations];
        if (numAnnotations > 0) {
            for (int i1 = 0; i1 < numAnnotations; i1++) {
                AttributeInfo.AttrInfo.Annotation annotation = new AttributeInfo.AttrInfo.Annotation();
                annotation.typeIndex = readU2().getValue();
                annotation.numPairs = readU2().getValue();
                annotation.pairs = readAnnotationPair(annotation.numPairs);
                annotations[i1] = annotation;
            }
        }
        return annotations;
    }

    private AttributeInfo.AttrInfo.AnnotationPair[] readAnnotationPair(int numAnnotations) {
        AttributeInfo.AttrInfo.AnnotationPair[] annotations = new AttributeInfo.AttrInfo.AnnotationPair[numAnnotations];
        if (numAnnotations > 0) {
            for (int i2 = 0; i2 < numAnnotations; i2++) {
                AttributeInfo.AttrInfo.AnnotationPair pair = new AttributeInfo.AttrInfo.AnnotationPair();
                pair.nameIndex = readU2().getValue();
                pair.elementValue = readElements(1)[0];
                annotations[i2] = pair;
            }
        }
        return annotations;
    }

    private AttributeInfo.AttrInfo.ElementValue[] readElements(int numValues) {
        AttributeInfo.AttrInfo.ElementValue[] elements = new AttributeInfo.AttrInfo.ElementValue[numValues];
        for (int i = 0; i < numValues; i++) {
            AttributeInfo.AttrInfo.ElementValue elementValue = new AttributeInfo.AttrInfo.ElementValue();
            char tag = (char) readBytes(1)[0];
            elementValue.tag = tag;
            switch (tag) {
                case 'B', 'C', 'D', 'F', 'I', 'J', 'S', 'Z', 's' -> elementValue.constIndex = readU2().getValue();
                case 'e' -> {
                    elementValue.typeNameIndex = readU2().getValue();
                    elementValue.constNameIndex = readU2().getValue();
                }
                case 'c' -> elementValue.classInfoIndex = readU2().getValue();
                case '@' -> elementValue.annotation = readAnnotations(1)[0];
                case '[' -> {
                    elementValue.numValues = readU2().getValue();
                    elementValue.values = readElements(elementValue.numValues);
                }
            }
            elements[i] = elementValue;
        }
        return elements;
    }

    private void methods() {
        int methodsCount = readU2().getValue();
        this.methods = new MethodInfo[methodsCount];
        for (int i = 0; i < methodsCount; i++) {
            MethodInfo methodInfo = new MethodInfo();
            methodInfo.accessFlags = readU2().getValue();
            methodInfo.nameIndex = readU2().getValue();
            methodInfo.name = getUtf8(methodInfo.nameIndex);
            methodInfo.descriptorIndex = readU2().getValue();
            methodInfo.descriptor = getUtf8(methodInfo.descriptorIndex);
            int attrsCount = readU2().getValue();
            methodInfo.attributesCount = attrsCount;
            methodInfo.attributes = resolveAttributes(attrsCount);
            this.methods[i] = methodInfo;
        }
    }

    private void attributes() {
        int attrsCount = readU2().getValue();
        this.attributesCount = attrsCount;
        this.attributes = resolveAttributes(attrsCount);
    }

    private String getUtf8(int index) {
        return (java.lang.String) constantPool.cpInfo[index].getValue(constantPool.cpInfo);
    }
}
