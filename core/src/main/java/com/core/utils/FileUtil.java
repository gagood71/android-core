package com.core.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.Date;

public class FileUtil {
    private static String getFormattedDate() {
        return DateUtil.FILE_NAME_FORMAT.format(new Date());
    }

    public static File getFile(String absolutePath) {
        if (TextUtils.isEmpty(absolutePath)) {
            throw new IllegalArgumentException("The parameter of absolutePath cannot be empty.");
        }

        return new File(absolutePath);
    }

    public static String getTemporaryJPG() {
        return getFormattedDate() + ".jpg";
    }

    public static String getTemporaryJPG(String preposition) {
        return getFormattedDate() + "-" + preposition + ".jpg";
    }

    public static String getCacheDirectory(Context context) {
        File cacheDirectory = context.getCacheDir();

        boolean isCreated;

        if (!cacheDirectory.exists()) {
            isCreated = cacheDirectory.mkdirs();
        } else {
            isCreated = true;
        }

        if (isCreated) {
            return cacheDirectory.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static String getCacheFile(Context context, String fileName) {
        String cacheDirectory = getCacheDirectory(context);

        if (!TextUtils.isEmpty(cacheDirectory)) {
            return new File(cacheDirectory, fileName).getAbsolutePath();
        } else {
            return null;
        }
    }
}
