package com.xiaobin.core.lox.tool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * created by xuweibin at 2024/12/19 13:55
 */
public class GenerateAst {

    public static void main(String[] args) throws Exception {
//        if(args.length != 1){
//            System.out.println("Usage: generate_ast <output directory>");
//            System.exit(64);
//        }
        String outputDir = "E:\\code\\git\\sourceTreeTool\\src\\main\\java\\com\\xiaobin\\core\\lox";

        defineAst(outputDir, "Expr", List.of(
                "Assign : Token name, Expr value",
                "Binary : Expr left, Token operator, Expr right",
                "Call : Expr callee, Token paren, List<Expr> arguments",
                "Grouping : Expr expression",
                "Literal : Object value",
                "Logical : Expr left, Token operator, Expr right",
                "Unary : Token operator, Expr right",
                "Variable : Token name"
        ));

        defineAst(outputDir, "Stmt", List.of(
                "Block : List<Stmt> statements",
                "Expression : Expr expression",
                "Function : Token name, List<Token> params, List<Stmt> body",
                "If : Expr condition, Stmt thenBranch, Stmt elseBrand",
                "Print : Expr expression",
                "Return : Token keyword, Expr value",
                "Var : Token name, Expr initializer",
                "While : Expr condition, Stmt body"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + File.separator + baseName + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {

            writer.println("package com.xiaobin.core.lox;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("abstract class " + baseName + " {");
            defineVisitor(writer, baseName, types);

            for (String type : types) {
                String className = type.split(":")[0].trim();
                String fields = type.split(":")[1].trim();
                defineType(writer, baseName, className, fields);
            }

            writer.println();
            writer.println("  abstract <R> R accept(Visitor<R> visitor);");

            writer.println("}");
        }
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("  interface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("    R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println("  }");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.println(" static class " + className + " extends " + baseName + " {");

        // constructor
        writer.println("  " + className + "(" + fieldList + ") {");
        String[] fields = fieldList.split(",");
        for (String field : fields) {
            String name = field.trim().split(" ")[1].trim();
            writer.println("    this." + name + " = " + name + ";");
        }
        writer.println("  }");

        // visitor pattern
        writer.println();
        writer.println("  @Override");
        writer.println("  <R> R accept(Visitor<R> visitor) {");
        writer.println("    return visitor.visit" + className + baseName + "(this);");
        writer.println("  }");

        // fields
        writer.println();
        for (String field : fields) {
            writer.println("  final " + field + ";");
        }

        writer.println("  }");
    }
}
