package com.core.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String ISO_8601 = "yyyy-MM-dd HH:mm:ss";

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_8601);

        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_8601);

        return simpleDateFormat.format(new Date(time));
    }
}
