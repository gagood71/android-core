package com.core;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

public class CompatApp extends Application {
    private static WeakReference<Context> ContextWeakReference;

    @Override
    public void onCreate() {
        super.onCreate();

        ContextWeakReference = new WeakReference<>(this);
    }

    public static Context getContext() {
        return ContextWeakReference.get();
    }
}
