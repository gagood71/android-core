package com.core.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
    );

    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat FILE_NAME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss",
            Locale.getDefault()
    );

    public static String getCurrentDate() {
        return DATE_FORMAT.format(Calendar.getInstance().getTime());
    }

    public static String getDate(long time) {
        return DATE_FORMAT.format(new Date(time));
    }
}
