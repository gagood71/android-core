package com.core.sql.lite.models;

public class Log {
    public String message;
    public String time;

    public Log(String log, String date) {
        message = log;
        time = date;
    }
}
