package com.xiaobin.core.java;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * created by xuweibin at 2025/1/7 17:20
 */
public class OpCodeGenerate {

    public static void main(String[] args) throws Exception {
        String outputPath = "E:\\code\\git\\sourceTreeTool\\src\\main\\java\\com\\xiaobin\\core\\java";

        generateOpCode(outputPath, List.of(
                "aaload 50",
                "aastore 83",
                "aconst_null 1",
                "aload 25, byte index",
                "aload_0 42",
                "aload_1 43",
                "aload_2 44",
                "aload_3 45",
                "anewarray 189, byte indexbyte1, byte indexbyte2",
                "areturn 176",
                "arraylength 190",
                "astore 58, byte index",
                "astore_0 75",
                "astore_1 76",
                "astore_2 77",
                "astore_3 78",
                "athrow 191",
                "baload 51",
                "bastore 84",
                "bipush 16, byte bb",
                "caload 52",
                "castore 85",
                "checkcast 192, byte indexbyte1, byte indexbyte2",
                "d2f 144",
                "d2i 142",
                "d2l 143",
                "dadd 99",
                "dalod 49",
                "dastore 82",
                "dcmpg 152",
                "dcmpl 151",
                "dconst_0 14",
                "dconst_1 15",
                "ddiv 111",
                "dload 24, byte index",
                "dload_0 38",
                "dload_1 39",
                "dload_2 40",
                "dload_3 41",
                "dmul 107",
                "dneg 119",
                "drem 115",
                "dreturn 175",
                "dstore 57, byte index",
                "dstore_0 71",
                "dstore_1 72",
                "dstore_2 73",
                "dstore_3 74",
                "dsub 103",
                "dup 89",
                "dup_x1 90",
                "dup_x2 91",
                "dup2 92",
                "dup2_x1 93",
                "dup2_x2 94",
                "f2d 141",
                "f2i 139",
                "f2l 140",
                "fadd 98",
                "faload 48",
                "fastore 81",
                "fcmpg 150",
                "fcmpl 149",
                "fconst_0 11",
                "fconst_1 12",
                "fconst_2 13",
                "fdiv 110",
                "fload 23, byte index",
                "fload_0 34",
                "fload_1 35",
                "fload_2 36",
                "fload_3 37",
                "fmul 106",
                "fneg 118",
                "frem 114",
                "freturn 174",
                "fstore 56, byte index",
                "fstore_0 67",
                "fstore_1 68",
                "fstore_2 69",
                "fstore_3 70",
                "fsub 102",
                "getfield 180, byte indexbyte1, byte indexbyte2",
                "getstatic 178, byte indexbyte1, byte indexbyte2",
                "goto 167, byte indexbyte1, byte indexbyte2",
                "goto_w 200, byte branchbyte1, byte branchbyte2, byte branchbyte3, byte branchbyte4",
                "i2b 145",
                "i2c 146",
                "i2d 135",
                "i2f 134",
                "i2l 133",
                "i2s 147",
                "iadd 96",
                "iaload 46",
                "iand 126",
                "iastore 79",
                "iconst_m1 2",
                "iconst_0 3",
                "iconst_1 4",
                "iconst_2 5",
                "iconst_3 6",
                "iconst_4 7",
                "iconst_5 8",
                "idiv 108",
                "if_acmpeq 165, byte branchbyte1, byte branchbyte2",
                "if_acmpne 166, byte branchbyte1, byte branchbyte2",
                "if_icmpeq 159, byte branchbyte1, byte branchbyte2",
                "if_icmpne 160, byte branchbyte1, byte branchbyte2",
                "if_icmplt 161, byte branchbyte1, byte branchbyte2",
                "if_icmpge 162, byte branchbyte1, byte branchbyte2",
                "if_icmpgt 163, byte branchbyte1, byte branchbyte2",
                "if_icmple 164, byte branchbyte1, byte branchbyte2",
                "ifeq 153, byte branchbyte1, byte branchbyte2",
                "ifne 154, byte branchbyte1, byte branchbyte2",
                "iflt 155, byte branchbyte1, byte branchbyte2",
                "ifge 156, byte branchbyte1, byte branchbyte2",
                "ifgt 157, byte branchbyte1, byte branchbyte2",
                "ifle 158, byte branchbyte1, byte branchbyte2",
                "ifnonnull 199, byte branchbyte1, byte branchbyte2",
                "ifnull 198, byte branchbyte1, byte branchbyte2",
                "iinc 132, byte index, byte constbyte",
                "iload 21, byte index",
                "iload_0 26",
                "iload_1 27",
                "iload_2 28",
                "iload_3 29",
                "imul 104",
                "ineg 116",
                "instanceof 193, byte indexbyte1, byte indexbyte2",
                "invokedynamic 186, byte indexbyte1, byte indexbyte2, byte indexbyte3, byte indexbyte4",
                "invokeinterface 185, byte indexbyte1, byte indexbyte2, byte count, byte zero",
                "invokespecial 183, byte indexbyte1, byte indexbyte2",
                "invokestatic 184, byte indexbyte1, byte indexbyte2",
                "invokevirtual 182, byte indexbyte1, byte indexbyte2",
                "ior 128",
                "irem 112",
                "ireturn 172",
                "ishl 120",
                "ishr 122",
                "istore 54, byte index",
                "istore_0 59",
                "istore_1 60",
                "istore_2 61",
                "istore_3 62",
                "isub 100",
                "iushr 124",
                "ixor 130",
                "jsr 168, byte indexbyte1, byte indexbyte2",
                "jsr_w 201, byte branchbyte1, byte branchbyte2, byte branchbyte3, byte branchbyte4",
                "l2d 138",
                "l2f 137",
                "l2i 136",
                "ladd 97",
                "laload 47",
                "land 127",
                "lastore 80",
                "lcmp 148",
                "lconst_0 9",
                "lconst_1 10",
                "ldc 18, byte index",
                "ldc_w 19, byte indexbyte1, byte indexbyte2",
                "ldc2_w 20, byte indexbyte1, byte indexbyte2",
                "ldiv 109",
                "lload 22, byte index",
                "lload_0 30",
                "lload_1 31",
                "lload_2 32",
                "lload_3 33",
                "lmul 105",
                "lneg 117",
                "lookupswitch 171, int defaultbyte1, int defaultbyte2, int defaultbyte3, int defaultbyte4, int npairs1, int npairs2, int npairs3, int npairs4, int[] matchOffsetsPairs",
                "lor 129",
                "lrem 113",
                "lreturn 173",
                "lshl 121",
                "lshr 123",
                "lstore 55, byte index",
                "lstore_0 63",
                "lstore_1 64",
                "lstore_2 65",
                "lstore_3 66",
                "lsub 101",
                "lushr 125",
                "lxor 131",
                "monitorenter 194",
                "monitorexit 195",
                "multianewarray 197, byte indexbyte1, byte indexbyte2, byte dims",
                "new 187, byte indexbyte1, byte indexbyte2",
                "newarray 188, byte atype",
                "nop 0",
                "pop 87",
                "pop2 88",
                "putfield 181, byte indexbyte1, byte indexbyte2",
                "putstatic 179, byte indexbyte1, byte indexbyte2",
                "ret 169, byte index",
                "return 177",
                "saload 53",
                "sastore 86",
                "sipush 17, byte sbyte1, byte sbyte2",
                "swap 95",
                "tableswitch 170, int defaultbyte1, int defaultbyte2, int defaultbyte3, int defaultbyte4, int lowbyte1, int lowbyte2, int lowbyte3, int lowbyte4, int highbyte1, int highbyte2, int highbyte3, int highbyte4, int[] offsets",
                "wide 196, OpCodes opcode, byte indexbyte1, byte indexbyte2, byte constbyte1, byte constbyte2"
        ));
    }

    private static void generateOpCode(String outputPath, List<String> opCodes) throws Exception{
        File outputFile = new File(outputPath);
        File opCodeFile = new File(outputFile, "OpCodes.java");
        try(PrintWriter writer = new PrintWriter(new FileWriter(opCodeFile))){
            writer.println("package com.xiaobin.core.java;");
            writer.println("/**");
            writer.println(" * created by xuweibin at " + LocalDateTime.now());
            writer.println(" */");

            writer.println("abstract class OpCodes {");
            writer.println();
            writer.println("  final int opCode;");
            writer.println("  final String name;");
            writer.println("  OpCodes(int opCode, String name){");
            writer.println("    this.opCode = opCode;");
            writer.println("    this.name = name;");
            writer.println("  }");
            writer.println();

            writer.println("  static OpCodes fromOpCode(int opCode){");
            writer.println("    return switch(opCode){");
            for (String opCode : opCodes) {
                String[] strings = opCode.split(",");
                String[] names = strings[0].split(" ");
                String clsName = names[0].substring(0, 1).toUpperCase() + names[0].substring(1);
                writer.printf("      case %s -> new %s();%n", names[1], clsName);
            }
            writer.println("      default -> null;");
            writer.println("    };");
            writer.println("  }");

            writer.println("  interface Visitor {");
            for (String opCode : opCodes) {
                String[] strings = opCode.split(",");
                String[] names = strings[0].split(" ");
                String clsName = names[0].substring(0, 1).toUpperCase() + names[0].substring(1);
                writer.printf("    void visit%s(%s _%s);%n", clsName, clsName, names[0]);
            }
            writer.println("  }");
            writer.println();

            writer.println("  abstract void accept(Visitor visitor);");
            writer.println();

            for (String opCode : opCodes) {
                String[] strings = opCode.split(",");
                String[] names = strings[0].split(" ");
                String clsName = names[0].substring(0, 1).toUpperCase() + names[0].substring(1);
                writer.printf("  static class %s extends OpCodes {%n", clsName);
                writer.println();
                writer.printf("    %s(){ super(%s, \"%s\"); }%n", clsName, names[1], names[0]);

                for (int i = 1; i < strings.length; i++) {
                    writer.println(strings[i] + ";");
                }
                writer.println();

                writer.println("    @Override");
                writer.println("    void accept(Visitor visitor) {");
                writer.println("      visitor.visit" + clsName + "(this);");
                writer.println("    }");
                writer.println("  }");
            }

            writer.println("}");
        }
    }
}
