package com.core.utils;

import android.util.Log;

public class LogUtil {
    public static void d(String tag, String message) {
        Log.d(getTag(tag), message);
    }

    public static void e(String tag, String message) {
        Log.e(getTag(tag), message);
    }

    public static void i(String tag, String message) {
        Log.i(getTag(tag), message);
    }

    public static void w(String tag, String message) {
        Log.i(getTag(tag), message);
    }

    private static String getTag(String tagString) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder tag = new StringBuilder();

        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.getClassName().contains(tagString)) {
                tag.append("(")
                        .append(stackTraceElement.getClassName())
                        .append(":")
                        .append(stackTraceElement.getLineNumber())
                        .append(")");
                break;
            }
        }

        return tag.toString();
    }
}
