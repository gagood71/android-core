package com.core.controllers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.core.activities.v1.CompatActivity;

import java.util.Arrays;

@SuppressWarnings("rawtypes")
public abstract class ActivityController<T extends CompatActivity>
        extends ViewController<T> {
    public ActivityController() {
        super();
    }

    protected boolean isPermissionsGranted(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        boolean isGranted = true;

        int i = 0;
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                isGranted = false;

                Log.e(tag, REQUEST_CODE + requestCode + PERMISSION_REJECT + permissions[i]);
                break;
            } else {
                Log.e(tag, REQUEST_CODE + requestCode + PERMISSION_AGREE + permissions[i]);
            }

            i++;
        }

        return isGranted;
    }

    /**
     * Activity > onActivityResult
     */
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        Log.e(tag, REQUEST_CODE + requestCode);
        Log.e(tag, RESULT_CODE + resultCode);
    }

    /**
     * Activity > onRequestPermissionsResult
     */
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.e(tag, REQUEST_CODE + requestCode);
        Log.e(tag, PERMISSIONS_REQUEST + Arrays.toString(permissions));
    }

    /**
     * Activity > onStart（多次觸發）
     */
    public abstract void onStart();

    /**
     * Activity > onPause
     */
    public abstract void onPause();

    /**
     * Activity > onDestroy
     */
    public abstract void onDestroy();
}
