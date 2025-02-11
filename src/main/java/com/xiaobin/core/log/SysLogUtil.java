package com.xiaobin.core.log;

/**
 * created by xuweibin at 2024/7/25 16:24
 */
public class SysLogUtil {

    private static final String RESET = "\u001B[0m";

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String BLACK = "\u001B[30m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String BLUE = "\u001B[34m";

    public static void logSuccessLn(String msg) {
        System.out.println(buildGreenText(msg));
    }

    public static void logSuccess(String msg) {
        System.out.print(buildGreenText(msg));
    }

    public static void logError(String msg) {
        System.out.println(buildRedText(msg));
    }

    public static void logError(String msg, Throwable e) {
        System.out.println(buildRedText(msg));
        e.printStackTrace();
    }

    public static void logWarn(String msg) {
        System.out.println(buildYellowText(msg));
    }

    public static void logWarnF(String msg, Object... values) {
        System.out.printf(buildYellowText(msg) + "%n", values);
    }

    public static void logNormalLn(String msg) {
        System.out.println(RESET + msg);
    }

    public static void logNormal(String msg) {
        System.out.print(RESET + msg);
    }

    public static void logNormalFLn(String msgFormat, Object... values) {
        System.out.println(RESET + msgFormat.formatted(values));
    }

    public static void logNormalF(String msgFormat, Object... values) {
        System.out.print(RESET + msgFormat.formatted(values));
    }

    public static String buildBlueText(String msg) {
        return BLUE + msg + RESET;
    }

    public static String buildCyanText(String msg) {
        return CYAN + msg + RESET;
    }

    public static String buildRedText(String msg) {
        return RED + msg + RESET;
    }

    public static String buildGreenText(String msg) {
        return GREEN + msg + RESET;
    }

    public static String buildWhiteText(String msg) {
        return WHITE + msg + RESET;
    }

    public static String buildYellowText(String msg) {
        return YELLOW + msg + RESET;
    }

    public static String buildBlackText(String msg) {
        return BLACK + msg + RESET;
    }

    public static String buildMagentaText(String msg) {
        return MAGENTA + msg + RESET;
    }

    public static String buildNormalText(String msg) {
        return RESET + msg;
    }
}
