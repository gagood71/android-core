package com.core.utils;

import java.util.Timer;
import java.util.TimerTask;

public interface TimerCallback {
    void onComplete(Timer timer, TimerTask timerTask);
}
