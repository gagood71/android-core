package com.core.utils;

import com.core.configuration.Configuration;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {
    public static void schedule(TimerCallback callback) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                callback.onComplete(timer, this);
            }
        };
        timer.schedule(timerTask, Configuration.Time.TIME_60000);
    }

    public static void schedule(TimerCallback callback, long time) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                callback.onComplete(timer, this);
            }
        };
        timer.schedule(timerTask, time);
    }

    public static void schedule(TimerCallback callback, long time, long period) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                callback.onComplete(timer, this);
            }
        };
        timer.schedule(timerTask, time, period);
    }
}
