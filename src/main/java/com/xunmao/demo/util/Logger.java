package com.xunmao.demo.util;

public class Logger {

    public static void logBefore(String proxyClassName, String methodName) {
        log("before", proxyClassName, methodName);
    }

    public static void logAfter(String proxyClassName, String methodName) {
        log("after", proxyClassName, methodName);
    }

    private static void log(String logType, String proxyClassName, String methodName) {

        switch (logType) {
            case "before":
                System.out.println(String.format("%s 代理: 即将调用 %s 方法", proxyClassName, methodName));
                break;
            case "after":
                System.out.println(String.format("%s 代理: 完成调用 %s 方法", proxyClassName, methodName));
                break;
            default:
                break;
        }
    }
}
